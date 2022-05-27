package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.R
import `in`.emmoney.app.databinding.FragmentHomePageBinding
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class HomePageFragment : Fragment(), ISchemesAdapter {

    private val TAG = "HomePageFragment"

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomePageViewModel

    private lateinit var adapter: SchemesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)
        // viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //Setting up recycler view
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SchemesAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter

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
            it?.let {
                Log.d(TAG, "$TAG: schemes list updated, size:${it.size}")
                adapter.updateList(it)
            }
        }
    }

    override fun onItemClicked(scheme: AllSchemesEntity) {
        Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Item clicked :${scheme.schemeName}")
    }


//    override fun onBackPressed() {
//        val a = Intent(Intent.ACTION_MAIN)
//        a.addCategory(Intent.CATEGORY_HOME)
//        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(a)
//    }
}