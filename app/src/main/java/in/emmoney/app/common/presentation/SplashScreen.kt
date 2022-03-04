package `in`.emmoney.app.common.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentSplashScreenBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashScreen : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflating the layout
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    // TODO 1: Updating the calls
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            findNavController().navigate(R.id.action_splashScreen_to_onboarding1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}