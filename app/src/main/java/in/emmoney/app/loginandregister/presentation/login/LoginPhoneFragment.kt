package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentLoginUsingPhoneBinding
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class LoginPhoneFragment : Fragment() {

    private val TAG = "auth"

    private var progressView: CustomProgressView? = null

    private var _binding: FragmentLoginUsingPhoneBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginPhoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginUsingPhoneBinding.inflate(inflater, container, false)

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
        binding.phone.doAfterTextChanged { text: Editable? ->
            viewModel.phoneNumber = text.toString()
        }
        binding.requestOtp.setOnClickListener {
            if (viewModel.isValidPhone()) {
                viewModel.setProgress(true)
                viewModel.sendOtp(requireActivity())
            }
        }

        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_register1Fragment)
        }

        binding.continueWithEmail.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_loginUsingEmailFragment)
        }
    }

    private fun setupObservers() {
        viewModel.phoneNumberError.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                binding.phone.error = error
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

        viewModel.getVerificationId().observe(viewLifecycleOwner) { vCode ->
            vCode?.let {
                Log.d(TAG, "observer: getVerificationId activated ")

                viewModel.setProgress(false)
                viewModel.setVCodeNull()

                viewModel.resetTimer()
//                if (viewModel.resendTxt.value.isNullOrEmpty())
//                    viewModel.startTimer()

                findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_loginUsingPhoneOTPFragment)
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
                    findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_homeActivity)
            }
        }
//            if (!userId.isNullOrEmpty() && viewModel.getCredential().value?.smsCode.isNullOrEmpty()) {
//                Log.d(TAG, "GOT userProfile, moving to next fragment")
//                Utils.toastLong(requireContext(), "OTP Verified")
//                findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_successLoginFragment)
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