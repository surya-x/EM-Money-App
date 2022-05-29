package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.R
import `in`.emmoney.app.common.presentation.CustomProgressView
import `in`.emmoney.app.common.utils.Utils.toggle
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.emmoney.app.databinding.FragmentSchemeBinding
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs


class SchemeFragment : Fragment() {

    private val TAG = "SchemeFragment"

    private var progressView: CustomProgressView? = null

    private var _binding: FragmentSchemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SchemeViewModel

    private val args: SchemeFragmentArgs by navArgs()

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
        progressView = CustomProgressView(requireContext())

        val schemeID = args.schemeID
        Log.d(TAG, "id = $schemeID" )

        setupObservers()
        viewModel.updateDataToViews(schemeID)
    }

    private fun setupObservers() {
        viewModel.schemeName.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.schemeFundTitle.text = it
                var about = resources.getString(R.string.about_fund)
                about = "$it $about"
                binding.aboutValue.text = about
            }
        })

        viewModel.latestNAV.observe(viewLifecycleOwner, Observer {
            it?.let {
                val floatNav = it.toFloatOrNull() // Return null if string is not entirely numbers or decimal
                val roundOff = String.format("%.2f", floatNav)
//                Log.d("roundoff", roundOff)
                binding.latestNavValue.text = roundOff
                binding.navDetail.text = roundOff
            }
        })

        viewModel.latestNAVDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val latestDate = "($it)"
                binding.latestNavDate.text = latestDate
            }
        })

        viewModel.fundType.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.fundTypeValue.text = it
            }
        })

        viewModel.fundCategory.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.fundCategoryValue.text = it
            }
        })

        viewModel.fundHouse.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.fundHouseValue.text = it
            }
        })

        viewModel.fundReturn.observe(viewLifecycleOwner, Observer {
            it?.let {
                val floatNav = it.toFloatOrNull() // Return null if string is not entirely numbers or decimal
                var roundOff = String.format("%.2f", floatNav)
                roundOff += "%"
//                val percentage = "$it%"
                binding.fundReturn.text = roundOff
            }
        })

        viewModel.getProgress().observe(viewLifecycleOwner) {
            progressView?.toggle(it)
        }
    }
}