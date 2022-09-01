package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.databinding.FragmentCompleteKycBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class CompleteKycFragment : Fragment() {

    private val TAG = "CompleteKycFragment"

    private var _binding: FragmentCompleteKycBinding? = null
    private val binding get() = _binding!!

//    private lateinit var viewModel: CompleteKycFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //        viewModel = ViewModelProvider(requireActivity()).get(LoginEmailViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(LoginEmailViewModel::class.java)

        // Inflate the layout for this fragment
        _binding = FragmentCompleteKycBinding.inflate(inflater, container, false)

//        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.submit.setOnClickListener {
            if(validateInput()){
                Toast.makeText(activity, "Details Submitted for review", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateInput() : Boolean{
        return if (binding.aadhar.text.isNotEmpty() and binding.phoneNumber.text
                .isNotEmpty() and binding.panCard.text.isNotEmpty() and binding.address1.text
                .isNotEmpty() and binding.address2.text.isNotEmpty() and binding.address3.text
                .isNotEmpty()
        ) {
            Log.d(TAG, "validateInput:${binding.aadhar.toString()}")
            Log.d(TAG, "validateInput:true")
            true
        } else {
            Log.d(TAG, "validateInput:false")
            Toast.makeText(activity, "Fields can't be empty!!", Toast.LENGTH_LONG).show()
            false
        }
    }

}