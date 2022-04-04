package `in`.emmoney.app

import `in`.emmoney.app.databinding.FragmentLoginUsingEmailBinding
import `in`.emmoney.app.databinding.FragmentProfilePageBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

class ProfilePageFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

    private var _binding: FragmentProfilePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)

//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }
}