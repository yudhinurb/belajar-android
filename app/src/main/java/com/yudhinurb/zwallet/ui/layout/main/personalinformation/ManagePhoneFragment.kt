package com.yudhinurb.zwallet.ui.layout.main.personalinformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentAddPhoneBinding
import com.yudhinurb.zwallet.databinding.FragmentManagePhoneBinding
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import com.yudhinurb.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ManagePhoneFragment : Fragment() {
    private lateinit var binding : FragmentManagePhoneBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadingDialog = LoadingDialog(requireActivity())
        binding = FragmentManagePhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getProfile().observe(viewLifecycleOwner){
            if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                binding.apply {
                    phoneNumber.text = it.resource.data?.phone
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            val response = viewModel.changeInfo(
                phone = ""
            )
            response.observe(viewLifecycleOwner){
                if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                    Toast.makeText(context, "Hapus nomor telepon berhasil", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                when (it.state) {
                    State.LOADING -> {
                        loadingDialog.start("Processing your request")
                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                            loadingDialog.stop()
                            findNavController().popBackStack()
                            Toast.makeText(context, "Hapus nomor telepon berhasil", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    State.ERROR -> {
                        loadingDialog.stop()
                        Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


}