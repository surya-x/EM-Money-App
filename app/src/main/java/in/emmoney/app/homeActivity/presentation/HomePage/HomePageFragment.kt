package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils.dismissIfShowing
import `in`.emmoney.app.common.utils.Utils.toggle
import `in`.emmoney.app.databinding.FragmentHomePageBinding
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class HomePageFragment : Fragment(), ISchemesAdapter {

    private val TAG = "HomePageFragment"

    private var progressView: CustomProgressView? = null

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

        // solution #1 based on chinese article
//        val params = binding.recyclerView.layoutParams
//        params.apply {
//            width = requireContext().resources.displayMetrics.widthPixels
//            height = requireContext().resources.displayMetrics.heightPixels
//        }

        // solution #2 -> https://stackoverflow.com/questions/44347883/loading-large-number-of-items-in-recycler-view
//        binding.recyclerView.setHasFixedSize(true)
//        binding.recyclerView.isNestedScrollingEnabled = false
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply { isAutoMeasureEnabled = false }


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.isNestedScrollingEnabled = false
        adapter = SchemesAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter

        viewModel.setSearchBarEnabled(false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // in here you can do logic when backPress is clicked
                if(viewModel.searchBarEnabled.value == true){
                    Log.d(TAG, "back button pressed -> searchEnabled = true")
                    binding.cardViewSearch.text.clear()
                    viewModel.setSearchBarEnabled(false)

                }else{
                    Log.d(TAG, "back button pressed -> searchEnabled = false")
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = CustomProgressView(requireContext())
//        testCrashlytics()
//        viewModel.fetchAllSchemes()
        setupListeners()
        setupObservers()
    }

    private fun setupListeners()  {
        binding.kycCard.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_completeKycFragment)
        }

        binding.viewAll.setOnClickListener {
            val action = HomePageFragmentDirections.actionHomePageFragmentToAllSchemeFragment("all")
            findNavController().navigate(action)
        }

        binding.gridLayout.setOnClickListener {
            val action = HomePageFragmentDirections.actionHomePageFragmentToAllSchemeFragment("selected")
            findNavController().navigate(action)
        }

        binding.cardViewSearch.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    Log.d(TAG, "Search button clicked")

                    if(binding.cardViewSearch.text.toString().isNotEmpty()){
//                        viewModel.setProgress(true)
                        viewModel.setSearchBarEnabled(true)
                    }
                    else
                        Toast.makeText(context, "Query can't be empty", Toast.LENGTH_SHORT).show()

                    true
                }
                else -> false
            }
        }
    }

    private fun setupObservers() {
        viewModel.allSchemes.observe(viewLifecycleOwner) {
//            viewModel.setProgress(false)
            it?.let {
                Log.d(TAG, "$TAG: schemes list updated, size:${it.size}")
                adapter.updateList(it)
                if(it.isEmpty()){
                    Toast.makeText(context, "No Fund Found!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.searchBarEnabled.observe(viewLifecycleOwner) {
            if(it == true){
                binding.title.visibility = View.GONE
                binding.gridLayout.visibility = View.GONE
                binding.kycCard.visibility = View.GONE

                viewModel.getSearchResults(binding.cardViewSearch.text.toString())
            }
            else{
                binding.title.visibility = View.VISIBLE
                binding.gridLayout.visibility = View.VISIBLE
                binding.kycCard.visibility = View.VISIBLE

                viewModel.fetchLimitedSchemes()
            }
        }

        viewModel.getProgress().observe(viewLifecycleOwner) {
            progressView?.toggle(it)
        }
    }

    override fun onItemClicked(scheme: AllSchemesEntity) {
//        Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Item clicked :${scheme.schemeCode}")

//        val schemeID: Integer = Integer(scheme.schemeCode)
        val action = HomePageFragmentDirections.actionHomePageFragmentToSchemeFragment(scheme.schemeCode)

        findNavController().navigate(action)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        try {
            progressView?.dismissIfShowing()
            _binding = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}