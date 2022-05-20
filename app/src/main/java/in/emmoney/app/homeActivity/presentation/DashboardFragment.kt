package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentDashboardBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DashboardFragment : Fragment() {

    private val TAG = "HomePageFragment"

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        return binding.root
    }
}