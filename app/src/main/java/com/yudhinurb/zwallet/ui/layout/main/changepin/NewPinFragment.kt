package com.yudhinurb.zwallet.ui.layout.main.changepin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import com.yudhinurb.zwallet.databinding.FragmentNewPinBinding
import com.yudhinurb.zwallet.ui.layout.auth.createpin.CreatePinViewModel
import com.yudhinurb.zwallet.ui.layout.main.MainActivity
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import com.yudhinurb.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class NewPinFragment : Fragment() {
    private lateinit var binding: FragmentNewPinBinding
    private val viewModel: CreatePinViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadingDialog = LoadingDialog(requireActivity())
        binding = FragmentNewPinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.btnLogin.setOnClickListener {
            val pin1 = binding.etOtp1.text.toString()
            val pin2 = binding.etOtp2.text.toString()
            val pin3 = binding.etOtp3.text.toString()
            val pin4 = binding.etOtp4.text.toString()
            val pin5 = binding.etOtp5.text.toString()
            val pin6 = binding.etOtp6.text.toString()

            val pin = pin1 + pin2 + pin3 + pin4 + pin5 + pin6

            val response = viewModel.createPin(
                pin.toInt()
            )

            response.observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                        loadingDialog.start("Processing your request")
                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                            Toast.makeText(context, "PIN berhasil diubah", Toast.LENGTH_LONG).show()
                            Handler().postDelayed({
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                loadingDialog.stop()
                                activity?.finish()}, 2000)
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