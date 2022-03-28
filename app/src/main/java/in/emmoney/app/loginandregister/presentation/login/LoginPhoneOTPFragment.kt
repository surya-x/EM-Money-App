package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentLoginUsingPhoneOTPBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginPhoneOTPFragment : Fragment() {

    private var _binding: FragmentLoginUsingPhoneOTPBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginUsingPhoneOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener {
            findNavController().navigate(R.id.action_loginUsingPhoneOTPFragment_to_successLoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}