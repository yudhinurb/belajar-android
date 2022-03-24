package com.yudhinurb.zwallet.ui.layout.main.transfersuccess

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentTransferSuccessBinding
import com.yudhinurb.zwallet.ui.layout.main.MainActivity
import com.yudhinurb.zwallet.ui.layout.main.findreceiver.ContactViewModel
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.utils.BASE_URL
import com.yudhinurb.zwallet.utils.Helper.formatPrice
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.net.ssl.HttpsURLConnection


class TransferSuccessFragment : Fragment() {
    private lateinit var binding: FragmentTransferSuccessBinding
    private val viewModel: ContactViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransferSuccessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnContinue.setOnClickListener {
            Handler().postDelayed({
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()}, 2000)
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

        homeViewModel.getBalance().observe(viewLifecycleOwner) {
            if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                binding.apply {
                    balanceAmount.formatPrice(it.resource.data?.get(0)?.balance.toString())
                }
            } else {
                Toast.makeText(context, "${it.resource?.message}", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getAmount().observe(viewLifecycleOwner){
            binding.transferAmount.formatPrice(it.amount.toString())
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
    }


}