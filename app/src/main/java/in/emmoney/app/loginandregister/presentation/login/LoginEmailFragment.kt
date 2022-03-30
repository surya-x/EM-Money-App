package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
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

    private var _binding: FragmentLoginUsingEmailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginEmailViewModel

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

        setupListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.email.doAfterTextChanged { email -> viewModel.email = email.toString() }
        binding.password.doAfterTextChanged { pass -> viewModel.password = pass.toString() }

        binding.forgetPassword.setOnClickListener {
            // TODO: on forget password click
            Toast.makeText(context, "Forget password needs to be implemented", Toast.LENGTH_LONG)
                .show()
        }
        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingEmailFragment_to_register1Fragment)
        }
        binding.login.setOnClickListener {
            viewModel.login()
        }
    }
}