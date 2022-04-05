package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
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


class Register1Fragment : Fragment() {

    private val TAG = "auth"

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

        viewModel = ViewModelProvider(requireActivity()).get(Register1ViewModel::class.java)


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
            if (viewModel.isValidOtp()) {
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

//                viewModel.resetTimer()
                if (viewModel.resendTxt.value.isNullOrEmpty())
                    viewModel.startTimer()
            }
        }

        viewModel.getFailed().observe(viewLifecycleOwner) {
            progressView?.dismiss()
        }

        viewModel.getTaskResult().observe(viewLifecycleOwner) { taskId ->
//            Log.d(TAG, "getTaskResult observer finds changes")

//            if (taskId != null && viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
            if (taskId != null) {
                Log.d(TAG, "getTaskResult observer finds changes")
                viewModel.fetchUser(taskId)
            }
        }

        viewModel.userProfileGot.observe(viewLifecycleOwner) { userId ->
            if (!userId.isNullOrEmpty()) {
                if (viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
                    Log.d(TAG, "getCredential && getCredential().value?.smsCode.isNullOrEmpty()")
                    Utils.toastLong(requireContext(), "OTP Verified")
                } else {
                    Log.d(TAG, "getCredential")
                    Utils.toastLong(requireContext(), "OTP Verified Automatically")
                }
                findNavController().navigate(R.id.action_register1Fragment_to_register2Fragment)
            }
        }
//            if (!userId.isNullOrEmpty() && viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
////            if (!userId.isNullOrEmpty()) {
//                Log.d(TAG, "GOT userProfile, moving to next fragment")
//                toastLong(
//                    requireContext(),
//                    "OTP Verified"
//                )
//                findNavController().navigate(R.id.action_register1Fragment_to_register2Fragment)
//            }
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