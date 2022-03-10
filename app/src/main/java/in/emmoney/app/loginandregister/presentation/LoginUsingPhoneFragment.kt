package `in`.emmoney.app.loginandregister.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentLoginUsingPhoneBinding
import `in`.emmoney.app.databinding.FragmentOnboarding3Binding
import androidx.navigation.fragment.findNavController

class LoginUsingPhoneFragment : Fragment() {

    private var _binding: FragmentLoginUsingPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginUsingPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.requestOtp.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_loginUsingPhoneOTPFragment)
        }

        binding.continueWithEmail.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_loginUsingEmailFragment)
        }

        binding.createNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingPhoneFragment_to_register1Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}