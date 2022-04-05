package `in`.emmoney.app

import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentMyAccountBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MyAccountFragment : Fragment() {

    val TAG = "account"

    private var progressView: CustomProgressView? = null

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = CustomProgressView(requireContext())
        progressView?.toggle(true)

        val noteRef = db.document("users/" + auth.currentUser?.uid)

        noteRef.get()
            .addOnSuccessListener { data ->
                Log.d(TAG, "noteRef.get():Success")
                if (data.exists()) {
                    // User with credential already created in Firestore DB
                    if(setAccountDetails(data)) {
                        progressView?.toggle(false)
                        Log.d(TAG, "write:Success")
                    }
                } else {
                    progressView?.toggle(false)
                    Log.d(TAG, "data.exists():false -> user new to firestore")
                    Toast.makeText(context, "Error Loading Details, please try again later", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                progressView?.toggle(false)
                Log.d(TAG, "noteRef.get():Failure: $e")
                Toast.makeText(context, "Error Loading Details, please try again later", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setAccountDetails(data: DocumentSnapshot) : Boolean{
        var fname = "N/A"
        var lname = "N/A"
        var phone = "N/A"
        var email = "N/A"
        var date = "N/A"

        fname = data.get("firstName").toString()
        lname = data.get("lastName").toString()
        phone = data.get("phoneNumber").toString()
        email = data.get("email").toString()
        val tempDate = auth.currentUser?.metadata?.creationTimestamp?.let { Date(it) }.toString()

        date = tempDate.split(" ")[1] + " " + tempDate.split(" ")[2]
        binding.firstName.setText(fname)
        binding.lastName.setText(lname)
        binding.phoneNumber.setText(phone)
        binding.email.setText(email)
        binding.accountCreatedDate.setText(date)

        return true
    }
}