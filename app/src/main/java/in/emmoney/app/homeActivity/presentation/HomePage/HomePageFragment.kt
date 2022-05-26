package `in`.emmoney.app.homeActivity.presentation.HomePage

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentHomePageBinding
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class HomePageFragment : Fragment() {

    private val TAG = "HomePageFragment"

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        testCrashlytics()
        viewModel.fetchAllSchemes()
        setupListeners()
        setupObservers()

    }

    private fun setupListeners() {
        binding.kycCard.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_completeKycFragment)
        }

    }

    private fun setupObservers() {
        binding.viewModel?.allSchemes?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(context, "schemes list found", Toast.LENGTH_LONG).show()
                Log.d(TAG, "$TAG: schemes list found, size:${it.size}")
            } else {
                Log.d(TAG, "$TAG: schemes list is empty")
            }
        }
    }


//    override fun onBackPressed() {
//        val a = Intent(Intent.ACTION_MAIN)
//        a.addCategory(Intent.CATEGORY_HOME)
//        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(a)
//    }
}