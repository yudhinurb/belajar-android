package com.yudhinurb.zwallet.ui.layout.main.inputamount

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentInputAmountBinding
import com.yudhinurb.zwallet.model.request.TransferRequest
import com.yudhinurb.zwallet.ui.layout.main.findreceiver.ContactViewModel
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.utils.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputAmountFragment : Fragment() {
    private lateinit var binding: FragmentInputAmountBinding
    private val viewModel: ContactViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputAmountBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
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

        homeViewModel.getBalance().observe(viewLifecycleOwner){
            binding.balanceAvailable.text = "Rp " + it.resource?.data?.get(0)?.balance.toString() + " Available"
        }

        binding.btnContinue.setOnClickListener {
            if (binding.inputAmount.text.isNullOrEmpty()){
                Toast.makeText(context, "Amount required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.setAmount(
                TransferRequest(
                    viewModel.getSelectedContact().value?.id.toString(),
                    binding.inputAmount.text.toString().toInt(),
                    binding.inputNotes.text.toString()
                )
            )
            Navigation.findNavController(view).navigate(R.id.action_inputAmountFragment_to_confirmationFragment)
        }
    }
}