package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentHomePageBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class HomePageFragment : Fragment() {

    private val TAG = "HomePageFragment"

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        testCrashlytics()
        setupListeners()

    }

    private fun setupListeners() {
        binding.kycCard.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_completeKycFragment)
        }

    }



//    override fun onBackPressed() {
//        val a = Intent(Intent.ACTION_MAIN)
//        a.addCategory(Intent.CATEGORY_HOME)
//        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(a)
//    }
}