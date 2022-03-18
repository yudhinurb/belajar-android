package com.yudhinurb.zwallet.ui.layout.main.inputamount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentInputAmountBinding
import com.yudhinurb.zwallet.ui.layout.main.findreceiver.ContactViewModel
import com.yudhinurb.zwallet.utils.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputAmountFragment : Fragment() {
    private lateinit var binding: FragmentInputAmountBinding
    private val viewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputAmountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.btnContinue.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_inputAmountFragment_to_confirmationFragment)
        }

        viewModel.getSelectedContact().observe(viewLifecycleOwner){
            binding.contactName.text = it.name
            binding.contactNumber.text = it.phone
            Glide.with(binding.imageContact).load(BASE_URL + it?.image)
                .apply(
                    RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.rumbling))
                .into(binding.imageContact)
        }
    }
}