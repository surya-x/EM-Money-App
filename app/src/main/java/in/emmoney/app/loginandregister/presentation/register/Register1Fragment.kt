package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.databinding.FragmentRegister1Binding
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class Register1Fragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private var isOtpVerified: Boolean = false
    private val TAG = "phone"

    private var _binding: FragmentRegister1Binding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: Register1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegister1Binding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(Register1ViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.phoneNumber.doAfterTextChanged { phone -> viewModel.phoneNumber = phone.toString().trim() }
        binding.otpPhone.doAfterTextChanged { otp -> viewModel.otpPhone = otp.toString().trim() }

        binding.continueButton.setOnClickListener {
            viewModel.sendOtp()
        }
    }

    private fun setupObservers() {
        viewModel.phoneNumberError.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.phoneNumber.error = it
            }
        })
        viewModel.otpError.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.otpPhone.error = it
            }
        })
        viewModel.otpRequestSent.observe(this) {
            if (it) {
                Log.d("Register", "request send observer activated ")
                binding.sendOtpButton.visibility = View.GONE
                binding.continueButton.visibility = View.VISIBLE
                binding.otpStatus.visibility = View.VISIBLE
                binding.otpPhone.visibility = View.VISIBLE
                binding.resend.visibility = View.VISIBLE

            }
        }
    }

}
//    private fun verifyPhoneNumber(phoneNumber: String){
//
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(requireActivity())                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    fun onContinueButtonClick() {
//        binding.continueButton.text = "Submit"
//    }
//
//    fun onSubmitButtonClick() {
//        findNavController().navigate(R.id.action_register1Fragment_to_register2Fragment)
//    }


////////////////////////////////////////////
//override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View? {
//
//    // Initialize Firebase Auth
//    auth = Firebase.auth
//
//    // Initialize phone auth callbacks
//    // [START phone_auth_callbacks]
//    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            // This callback will be invoked in two situations:
//            // 1 - Instant verification. In some cases the phone number can be instantly
//            //     verified without needing to send or enter a verification code.
//            // 2 - Auto-retrieval. On some devices Google Play services can automatically
//            //     detect the incoming verification SMS and perform verification without
//            //     user action.
//            Log.d(TAG, "onVerificationCompleted:$credential")
//            isOtpVerified = true
//            onSubmitButtonClick()
//        }
//        override fun onVerificationFailed(e: FirebaseException) {
//            // This callback is invoked in an invalid request for verification is made,
//            // for instance if the the phone number format is not valid.
//            Log.w(TAG, "onVerificationFailed", e)
//            if (e is FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//            } else if (e is FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//            }
//            // Show a message and update the UI
//        }
//
//        override fun onCodeSent(
//            verificationId: String,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            // The SMS verification code has been sent to the provided phone number, we
//            // now need to ask the user to enter the code and then construct a credential
//            // by combining the code with a verification ID.
//            Log.d(TAG, "onCodeSent:$verificationId")
//            // Save verification ID and resending token so we can use them later
//            storedVerificationId = verificationId
//            resendToken = token
//        }
//    }
//    // [END phone_auth_callbacks]
//
//
//
//    // Inflate the layout for this fragment
//    _binding = FragmentRegister1Binding.inflate(inflater, container, false)
//    return binding.root
//}
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//
//    binding.continueButton.setOnClickListener {
//        onContinueButtonClick()
//
//        // val phoneNumber = binding.phoneNumber.text.toString()
//        val phoneNumber = "+91" + binding.phoneNumber.text.toString()
//        Toast.makeText(context,"OTP Sent to $phoneNumber, trying to auto detect", Toast.LENGTH_LONG).show()
//        verifyPhoneNumber(phoneNumber)
//    }
//}