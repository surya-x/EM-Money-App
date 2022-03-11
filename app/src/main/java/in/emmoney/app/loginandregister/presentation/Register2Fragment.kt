package `in`.emmoney.app.loginandregister.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentRegister2Binding
import android.util.Log
import java.util.concurrent.TimeUnit
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register2Fragment : Fragment() {



    private var _binding: FragmentRegister2Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        // Inflate the layout for this fragment
        _binding = FragmentRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

////        val phoneNumber = binding.phoneNumber.text.toString()
//        val phoneNumber = "+916005775031"
//        verifyPhoneNumber(phoneNumber)
//
        binding.signUp.setOnClickListener {
            onSubmitButtonClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
    private fun onSubmitButtonClick() {
        findNavController().navigate(R.id.action_register2Fragment_to_successLoginFragment)
    }
}