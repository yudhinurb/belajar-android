package com.yudhinurb.zwallet.ui.layout.main.changepassword

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentChangePasswordBinding
import com.yudhinurb.zwallet.databinding.FragmentEnterPinBinding
import com.yudhinurb.zwallet.ui.layout.main.MainActivity
import com.yudhinurb.zwallet.ui.layout.main.findreceiver.ContactViewModel
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import com.yudhinurb.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadingDialog = LoadingDialog(requireActivity())
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.inputRepeatPassword.addTextChangedListener {
            if (binding.inputRepeatPassword.text.length > 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_login_active)
                binding.btnLogin.setTextColor(Color.parseColor("#FFFFFF"))
            } else if (binding.inputRepeatPassword.text.length <= 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_login)
                binding.btnLogin.setTextColor(Color.parseColor("#9DA6B5"))
            }
        }

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.inputRepeatPassword.text.length < 8 || binding.inputNewPassword.text.length < 8 || binding.inputOldPassword.text.length < 8){
                Toast.makeText(context, "Complete the form", Toast.LENGTH_SHORT).show()
            } else if (binding.inputNewPassword.text.toString() != binding.inputRepeatPassword.text.toString()) {
                Toast.makeText(context, "New password and repeat password must be the same", Toast.LENGTH_SHORT).show()
            } else {
                val response = viewModel.changePassword(
                    binding.inputOldPassword.text.toString(),
                    binding.inputNewPassword.text.toString()
                )

                response.observe(viewLifecycleOwner) {
                    when (it.state) {
                        State.LOADING -> {
                            loadingDialog.start("Processing your request")
                        }
                        State.SUCCESS -> {
                            if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                                Handler().postDelayed({
                                    val intent = Intent(activity, MainActivity::class.java)
                                    Toast.makeText(context, "Ubah password berhasil", Toast.LENGTH_LONG).show()
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


}