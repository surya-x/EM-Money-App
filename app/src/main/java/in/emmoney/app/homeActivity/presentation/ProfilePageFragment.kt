package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.MainActivity
import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentProfilePageBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class ProfilePageFragment : Fragment() {

    private val TAG = "ProfilePageFragment"

    private val auth = FirebaseAuth.getInstance()
    private val currentUser = MutableLiveData(auth.currentUser)

    private var _binding: FragmentProfilePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var phoneOut: String = ""
        val phone = auth.currentUser?.phoneNumber
        if (phone != null) {
            phoneOut = phone.substring(0, 3) + " " + phone.substring(3, 8) + " " + phone.substring(8);
        }

//        var emailOut: String = ""
//        val email = auth.currentUser?.e
//        if (phone != null) {
//            phoneOut = phone.substring(0, 3) + " " + phone.substring(3, 8) + " " + phone.substring(8);
//        }

//        binding.name.text = auth.currentUser?.displayName
//        binding.phoneNumber.text = phoneOut
        binding.name.text = phoneOut

        setupListeners()
        setupObservers()
    }


    private fun setupListeners() {
        binding.myAccount.setOnClickListener {
            findNavController().navigate(R.id.action_profilePageFragment_to_myAccountFragment)
        }

        binding.logout.setOnClickListener {
            Log.d(TAG, "logout button clicked")
            auth.signOut()
            Log.d(TAG, "auth.signOut() completed")
            currentUser.value = auth.currentUser
//            activity?.finish()
//            System.exit(0)
        }

        binding.myTransactions.setOnClickListener {
            Toast.makeText(context, "Under Construction!!", Toast.LENGTH_SHORT).show()
        }
        binding.helpSupport.setOnClickListener {
            Toast.makeText(context, "Under Construction!!", Toast.LENGTH_SHORT).show()
        }
        binding.termsService.setOnClickListener {
            Toast.makeText(context, "Under Construction!!", Toast.LENGTH_SHORT).show()
        }
        binding.appSettings.setOnClickListener {
            Toast.makeText(context, "Under Construction!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                Log.d(TAG, "observer activated: current user found null")
//                activity?.finish()
//                exitProcess(0)

                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("FLAG_LOGGED_IN", false)
                startActivity(intent)
            }
        }


    }
}