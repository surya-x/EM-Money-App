package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
import `in`.emmoney.app.common.utils.Utils.toastLong
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentRegister1Binding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider


class Register1Fragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

    private val TAG = "register"

    private var progressView: CustomProgressView? = null

    private var _binding: FragmentRegister1Binding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: Register1ViewModel

//    private val viewModel by activityViewModels<Register1ViewModel>()
//    private val viewModel: Register1ViewModel by activityViewModels()
//    private val viewModel: Register1ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegister1Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(Register1ViewModel::class.java)


//        viewModel = ViewModelProvider(requireActivity()).get(Register1ViewModel::class.java)
//        viewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication(context))
//            )[Register1ViewModel::class.java]
//        viewModel = ViewModelProviders.of(this).get(Register1ViewModel::class.java)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = CustomProgressView(requireContext())

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.phoneNumber.doAfterTextChanged { phone ->
            viewModel.phoneNumber = phone.toString()
        }
        binding.otpPhone.doAfterTextChanged { otp ->
            viewModel.otpPhone = otp.toString()
        }

        binding.sendOtpButton.setOnClickListener {
            // TODO: 1. Check for no internet
            //       2. country code*
            if (viewModel.isValidPhone()) {
                viewModel.setProgress(true)
                viewModel.sendOtp(requireActivity())
            }
        }

        binding.continueButton.setOnClickListener {
            if(viewModel.isValidOtp()){
                viewModel.setProgress(true)
                viewModel.verifyOtp()
            }
        }
    }

    private fun setupObservers() {
        viewModel.phoneNumberError.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.phoneNumber.error = it
            }
        })

        viewModel.otpError.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.otpPhone.error = it
            }
        })

        viewModel.getProgress().observe(viewLifecycleOwner) {
            progressView?.toggle(it)
        }

        viewModel.otpError.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.otpPhone.error = it
            }
        })

        /* To know when the OTP code has been sent by firebase in AuthRepo */
        viewModel.getVerificationId().observe(viewLifecycleOwner) { vCode ->
            vCode?.let {
                Log.d("register", "request send observer activated ")

                viewModel.setProgress(false)
                viewModel.setVCodeNull()

                binding.sendOtpButton.visibility = View.GONE
                binding.continueButton.visibility = View.VISIBLE
                binding.otpStatus.visibility = View.VISIBLE
                binding.otpPhone.visibility = View.VISIBLE
                binding.resendText.visibility = View.VISIBLE

            }
        }

        viewModel.getFailed().observe(viewLifecycleOwner) {
            progressView?.dismiss()
        }

        viewModel.getTaskResult().observe(viewLifecycleOwner) { taskId ->
            Log.d(TAG, "getTaskResult observer finds changes")

            if (taskId != null && viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
                Log.d(TAG, "getTaskResult observer finds changes")
                viewModel.fetchUser(taskId)
            }
        }

        viewModel.userProfileGot.observe(viewLifecycleOwner) { userId ->
            if (!userId.isNullOrEmpty() && viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
                Log.d(TAG, "GOT userProfile, moving to next fragment")
                toastLong(
                    requireContext(),
                    "OTP Verified"
                )
                findNavController().navigate(R.id.action_register1Fragment_to_register2Fragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            progressView?.dismissIfShowing()
            _binding = null
        } catch (e: Exception) {
            e.printStackTrace()
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