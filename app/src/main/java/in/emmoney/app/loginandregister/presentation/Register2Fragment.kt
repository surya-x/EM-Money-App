package `in`.emmoney.app.loginandregister.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentRegister2Binding
import android.util.Log
import java.util.concurrent.TimeUnit
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register2Fragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private val TAG = "phone"

    private var _binding: FragmentRegister2Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
                    Log.d(TAG, "onVerificationCompleted:$credential")
                    onSubmitButtonClick()
                }
                override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                    Log.w(TAG, "onVerificationFailed", e)
                    if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                    } else if (e is FirebaseTooManyRequestsException) {
                 // The SMS quota for the project has been exceeded
                    }
                // Show a message and update the UI
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d(TAG, "onCodeSent:$verificationId")
                    // Save verification ID and resending token so we can use them later
                    storedVerificationId = verificationId
                    resendToken = token
                }
            }
            // [END phone_auth_callbacks]



        // Inflate the layout for this fragment
        _binding = FragmentRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val phoneNumber = binding.phoneNumber.text.toString()
        val phoneNumber = "+916005775031"
        verifyPhoneNumber(phoneNumber)

        binding.signUp.setOnClickListener {
            onSubmitButtonClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifyPhoneNumber(phoneNumber: String){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun onSubmitButtonClick() {
        findNavController().navigate(R.id.action_register2Fragment_to_successLoginFragment)
    }
}