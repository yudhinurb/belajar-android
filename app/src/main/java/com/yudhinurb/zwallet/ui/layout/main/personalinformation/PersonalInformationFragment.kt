package com.yudhinurb.zwallet.ui.layout.main.personalinformation

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentPersonalInformationBinding
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.ui.layout.viewModelsFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class PersonalInformationFragment : Fragment() {
    private lateinit var binding: FragmentPersonalInformationBinding
    private val viewModel: HomeViewModel by activityViewModels()

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
                if (it.resource.data?.phone.isNullOrEmpty()) {
                    binding.cardPhoneNumberAssigned.visibility = View.GONE
                    binding.cardPhoneNumberUnassigned.visibility = View.VISIBLE
                } else {
                    binding.cardPhoneNumberAssigned.visibility = View.VISIBLE
                    binding.cardPhoneNumberUnassigned.visibility = View.GONE
                }
                binding.apply {
                    firstName.text = it.resource.data?.firstname
                    lastName.text = it.resource.data?.lastname
                    verifiedEmail.text = it.resource.data?.email
                    phoneNumber.text = it.resource.data?.phone
                }
            }
        }

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.textAddPhoneNumber.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_personalInformationFragment_to_addPhoneFragment)
        }

        binding.textManage.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_personalInformationFragment_to_managePhoneFragment)
        }
    }

}