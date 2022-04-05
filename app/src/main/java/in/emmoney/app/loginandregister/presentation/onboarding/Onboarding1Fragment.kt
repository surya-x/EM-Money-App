package `in`.emmoney.app.loginandregister.presentation.onboarding

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentOnboarding1Binding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class Onboarding1Fragment : Fragment() {

    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout
        _binding = FragmentOnboarding1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding1_to_onboarding2)
        }

        // TODO: For debug mode only
//        binding.imageView2.setOnClickListener {
//            findNavController().navigate(R.id.action_onboarding1_to_homeActivity)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}