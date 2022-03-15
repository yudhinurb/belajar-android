package com.yudhinurb.zwallet.ui.main.personalinformation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentHomeBinding
import com.yudhinurb.zwallet.databinding.FragmentPersonalInformationBinding
import com.yudhinurb.zwallet.ui.main.MainActivity
import com.yudhinurb.zwallet.ui.main.home.HomeViewModel
import com.yudhinurb.zwallet.ui.viewModelsFactory
import com.yudhinurb.zwallet.utils.*
import javax.net.ssl.HttpsURLConnection

class PersonalInformationFragment : Fragment() {
    private lateinit var binding: FragmentPersonalInformationBinding
    private val viewModel: HomeViewModel by viewModelsFactory { HomeViewModel(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPersonalInformationBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        viewModel.getProfile().observe(viewLifecycleOwner){
            if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                binding.apply {
                    firstName.text = it.resource.data?.firstname
                    lastName.text = it.resource.data?.lastname
                    verifiedEmail.text = it.resource.data?.email
                    phoneNumber.text = it.resource.data?.phone
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_personalInformationFragment_to_profileFragment)
        }
    }

}