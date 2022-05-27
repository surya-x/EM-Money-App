package `in`.emmoney.app.homeActivity.presentation.homePage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.emmoney.app.databinding.FragmentSchemeBinding

class SchemeFragment : Fragment() {

    private val TAG = "HomePageFragment"

    private var _binding: FragmentSchemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SchemeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSchemeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(SchemeViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}