package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentLoginUsingPhoneOTPBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class LoginPhoneOTPFragment : Fragment() {

    private val TAG = "auth"

    private var progressView: CustomProgressView? = null

    private var _binding: FragmentLoginUsingPhoneOTPBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginPhoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginUsingPhoneOTPBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(LoginPhoneViewModel::class.java)

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
        binding.otp.doAfterTextChanged { otp ->
            viewModel.otpPhone = otp.toString()
        }
        binding.submit.setOnClickListener {
            if (viewModel.isValidOtp()) {
                viewModel.setProgress(true)
                viewModel.verifyOtp()
            }
        }
    }

    private fun setupObservers() {

        viewModel.otpError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.otp.error = it
            }
        }

        viewModel.getProgress().observe(viewLifecycleOwner) {
            progressView?.toggle(it)
        }

        viewModel.getFailed().observe(viewLifecycleOwner) {
            progressView?.dismiss()
        }

        viewModel.getTaskResult().observe(viewLifecycleOwner) { taskId ->
//            Log.d(TAG, "getTaskResult observer finds changes - 1")

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
                findNavController().navigate(R.id.action_loginUsingPhoneOTPFragment_to_successLoginFragment)
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

//    private fun checkOtpVerified() {
////        if (!viewModel.userProfileGot.value.isNullOrEmpty() && viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
////            Log.d(TAG, "GOT userProfile, moving to next fragment")
////            Utils.toastLong(requireContext(), "OTP Verified Automatically")
////            findNavController().navigate(R.id.action_loginUsingPhoneOTPFragment_to_successLoginFragment)
////        }
//    }
