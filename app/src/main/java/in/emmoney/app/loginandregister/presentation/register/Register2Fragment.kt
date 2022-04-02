package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentRegister2Binding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class Register2Fragment : Fragment() {

    private val TAG = "auth"

    private lateinit var viewModel: Register1ViewModel

    private var _binding: FragmentRegister2Binding? = null
    private val binding get() = _binding!!

    private var progressView: CustomProgressView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentRegister2Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(Register1ViewModel::class.java)

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
        binding.firstName.doAfterTextChanged { fname ->
            viewModel.firstName = fname.toString()
        }
        binding.lastName.doAfterTextChanged { lname ->
            viewModel.lastName = lname.toString()
        }
        binding.email.doAfterTextChanged { email ->
            viewModel.email = email.toString()
        }
        binding.password.doAfterTextChanged { password ->
            viewModel.password = password.toString()
        }

        binding.signUp.setOnClickListener {
            if (viewModel.isValidEntries()) {
                viewModel.setProgress(true)
                viewModel.linkUserWithEmail()
            }
        }
    }

    private fun setupObservers() {
        viewModel.getEmailAuthTaskResult().observe(viewLifecycleOwner) { Task ->
            if (Task.result.user != null){
                Log.d(TAG, "observer: emailAuthResult user")
                viewModel.createUserInDb(Task.result.user!!)
            }
            else {
                Log.d(TAG, "ERROR: user not found while linking phoneAuth with emailAuth")
            }
//            if (authResult != null && viewModel.getCredential().value?.signInMethod.isNullOrEmpty()) {
//                Log.d(TAG, "getTaskResult observer finds changes")
//                viewModel.fetchUser(taskId)
//            }
        }

        viewModel.isUserRegistered().observe(viewLifecycleOwner) {
            if (it){
                findNavController().navigate(R.id.action_register2Fragment_to_successLoginFragment)
            }
        }

        viewModel.firstNameError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.firstName.error = it
            }
        }
        viewModel.lastNameError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.lastName.error = it
            }
        }
        viewModel.emailError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.email.error = it
            }
        }
        viewModel.passwordError.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.password.error = it
            }
        }
        viewModel.getProgress().observe(viewLifecycleOwner) {
            progressView?.toggle(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            viewModel.clearAll()
            progressView?.dismissIfShowing()
            _binding = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}