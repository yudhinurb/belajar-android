package com.yudhinurb.zwallet.ui.layout.main.confirmation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentConfirmationBinding
import com.yudhinurb.zwallet.databinding.FragmentEnterPinBinding
import com.yudhinurb.zwallet.databinding.FragmentInputAmountBinding
import com.yudhinurb.zwallet.ui.layout.main.findreceiver.ContactViewModel
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.utils.BASE_URL
import com.yudhinurb.zwallet.utils.Helper.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class ConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentConfirmationBinding
    private val viewModel: ContactViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentConfirmationBinding.inflate(layoutInflater)
        return binding.root
    }

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

        viewModel.getAmount().observe(viewLifecycleOwner){
            binding.transferAmount.formatPrice(it.amount.toString())
            binding.balanceAmount.text = "Masih bingung ngitungnya"
            // format date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mma")
                val answer =  current.format(formatter)
                binding.dateTime.text = answer
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("MMM dd, yyyy - HH:mma")
                val answer = formatter.format(date)
                binding.dateTime.text = answer
            }
            if (it.notes.isEmpty()) {
                binding.notes.text = "-"
            } else {
                binding.notes.text = it.notes
            }

        }

        binding.btnContinue.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_confirmationFragment_to_enterPinFragment)
        }
    }
}
