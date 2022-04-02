package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentLoginUsingEmailBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class LoginEmailFragment : Fragment() {

    private val TAG = "auth"

    private var _binding: FragmentLoginUsingEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginEmailViewModel

    private var progressView: CustomProgressView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        viewModel = ViewModelProvider(requireActivity()).get(LoginEmailViewModel::class.java)
        viewModel = ViewModelProvider(this).get(LoginEmailViewModel::class.java)

        // Inflate the layout for this fragment
        _binding = FragmentLoginUsingEmailBinding.inflate(inflater, container, false)

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
        binding.email.doAfterTextChanged { email -> viewModel.email = email.toString() }
        binding.password.doAfterTextChanged { pass -> viewModel.password = pass.toString() }

        binding.login.setOnClickListener {
            if(viewModel.isValidInput()){
                viewModel.setProgress(true)
                viewModel.login()
            }
        }

        binding.forgetPassword.setOnClickListener {
            // TODO: on forget password click
            Toast.makeText(context, "Forget password needs to be implemented", Toast.LENGTH_LONG)
                .show()
        }

        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingEmailFragment_to_register1Fragment)
        }
    }

    private fun setupObservers() {
        viewModel.getUserLoggedIn().observe(viewLifecycleOwner) { firebaseUser ->
            if(firebaseUser != null){
                findNavController().navigate(R.id.action_loginUsingEmailFragment_to_successLoginFragment)
            }
        }
        viewModel.getFailed().observe(viewLifecycleOwner) {
            progressView?.dismiss()
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
            progressView?.dismissIfShowing()
            _binding = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
