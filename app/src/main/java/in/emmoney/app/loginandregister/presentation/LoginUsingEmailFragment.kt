package `in`.emmoney.app.loginandregister.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentLoginUsingEmailBinding
import `in`.emmoney.app.databinding.FragmentLoginUsingPhoneBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class LoginUsingEmailFragment : Fragment() {

    private var _binding: FragmentLoginUsingEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginUsingEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingEmailFragment_to_successLoginFragment)
        }

        binding.forgetPassword.setOnClickListener {
            Toast.makeText(context, "Forget password needs to be implemented", Toast.LENGTH_LONG).show();
        }

        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingEmailFragment_to_register1Fragment)
        }
    }
}