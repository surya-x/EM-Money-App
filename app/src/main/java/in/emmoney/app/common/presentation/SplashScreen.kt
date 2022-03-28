package `in`.emmoney.app.common.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentSplashScreenBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashScreen : Fragment() {

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
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            updateUI();
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_splashScreen_to_onboarding1)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            if(onView) {
                findNavController().navigate(R.id.action_splashScreen_to_onboarding1)
            }
        }, 3000)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        onView = false
    }

    private fun updateUI(){
        findNavController().navigate(R.id.action_splashScreen_to_successLoginFragment)
    }
}