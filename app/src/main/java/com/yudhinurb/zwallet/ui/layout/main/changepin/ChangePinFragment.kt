package com.yudhinurb.zwallet.ui.layout.main.changepin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentChangePinBinding
import com.yudhinurb.zwallet.databinding.FragmentPersonalInformationBinding
import com.yudhinurb.zwallet.ui.layout.main.findreceiver.ContactViewModel
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import com.yudhinurb.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ChangePinFragment : Fragment() {
    private lateinit var binding: FragmentChangePinBinding
    private val viewModel: ContactViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadingDialog = LoadingDialog(requireActivity())
        binding = FragmentChangePinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnLogin.setOnClickListener{
            val pin1 = binding.etOtp1.text.toString()
            val pin2 = binding.etOtp2.text.toString()
            val pin3 = binding.etOtp3.text.toString()
            val pin4 = binding.etOtp4.text.toString()
            val pin5 = binding.etOtp5.text.toString()
            val pin6 = binding.etOtp6.text.toString()

            val pin = pin1 + pin2 + pin3 + pin4 + pin5 + pin6

            if (pin.length < 6) {
                Toast.makeText(context, "Lengkapi PIN", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.checkPin(pin.toInt()).observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                        loadingDialog.start("Checking PIN")
                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                            Navigation.findNavController(view).navigate(R.id.action_changePinFragment_to_newPinFragment)
                            loadingDialog.stop()
                        } else {
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    State.ERROR -> {
                        loadingDialog.stop()
                        Toast.makeText(context, "PIN Salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}