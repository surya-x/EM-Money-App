package `in`.emmoney.app.common.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentSplashScreenBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashScreen : Fragment() {

    private val TAG = "auth"
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private var onView: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Inflating the layout
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

//        Log.d(TAG, "checking if user already signed in")
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            Log.d(TAG, "currentUser != null, user already logged in: ${auth.currentUser?.uid.toString()}")
//            updateUI()
//        }
//        else{
//            Handler(Looper.getMainLooper()).postDelayed({
//                if(onView) {
//                    findNavController().navigate(R.id.action_splashScreen_to_onboarding1)
//                }
//            }, 2000)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Create an Intent that will start the Menu-Activity. */
        Handler(Looper.getMainLooper()).postDelayed({
            if(onView) {
                findNavController().navigate(R.id.action_splashScreen_to_onboarding1)
            }
        }, 2000)

//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            updateUI()
//        }
//        else{
//            Handler(Looper.getMainLooper()).postDelayed({
//                if(onView) {
//                    findNavController().navigate(R.id.action_splashScreen_to_onboarding1)
//                }
//            }, 2000)
//        }

        // TODO: For debug mode only
//        binding.imageView.setOnClickListener {
//            updateUI()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        onView = false
    }

    private fun updateUI(){
        if(onView)
            findNavController().navigate(R.id.action_splashScreen_to_homeActivity)
    }
}