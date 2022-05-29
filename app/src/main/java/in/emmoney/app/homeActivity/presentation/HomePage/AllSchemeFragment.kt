package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.databinding.FragmentAllSchemeBinding
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

class AllSchemeFragment : Fragment(), ISchemesAdapter {

    private val TAG = "HomePageFragment"

    private var _binding: FragmentAllSchemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AllSchemeViewModel
    private lateinit var adapter: SchemesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentAllSchemeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(AllSchemeViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SchemesAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter

        setupObservers()
        viewModel.fetchAllSchemes()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.allSchemes.observe(viewLifecycleOwner) {
            it?.let {
                Log.d(TAG, "$TAG: schemes list updated, size:${it.size}")
                adapter.updateList(it)
            }
        }
    }


    override fun onItemClicked(scheme: AllSchemesEntity) {
        Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Item clicked :${scheme.schemeCode}")

//        val schemeID: Integer = Integer(scheme.schemeCode)
        val action = AllSchemeFragmentDirections.actionAllSchemeFragmentToSchemeFragment(scheme.schemeCode)

        findNavController().navigate(action)
    }
}