package com.yudhinurb.zwallet.ui.layout.auth.register

import android.content.Intent
import android.content.SharedPreferences
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
import androidx.navigation.Navigation
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentRegisterBinding
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.request.RegisterRequest
import com.yudhinurb.zwallet.model.request.TransferRequest
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.ui.layout.auth.AuthActivity
import com.yudhinurb.zwallet.ui.layout.auth.login.LoginViewModel
import com.yudhinurb.zwallet.ui.layout.main.MainActivity
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import com.yudhinurb.zwallet.utils.*
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadingDialog = LoadingDialog(requireActivity())
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.inputPassword.addTextChangedListener {
            if (binding.inputPassword.text.length > 8) {
                binding.btnSignUp.setBackgroundResource(R.drawable.background_button_login_active)
                binding.btnSignUp.setTextColor(Color.parseColor("#FFFFFF"))
            } else if (binding.inputPassword.text.length <= 8) {
                binding.btnSignUp.setBackgroundResource(R.drawable.background_button_login)
                binding.btnSignUp.setTextColor(Color.parseColor("#9DA6B5"))
            }
        }

        binding.btnSignUp.setOnClickListener{
            if (binding.inputUsername.text.isNullOrEmpty() || binding.inputEmail.text.isNullOrEmpty() || binding.inputPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "complete the form", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val response = viewModel.register(
                binding.inputUsername.text.toString(),
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )
            response.observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {
                        loadingDialog.start("Processing your request")
                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                            viewModel.setEmail(binding.inputEmail.text.toString())
                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_otpFragment)
                            loadingDialog.stop()
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

//            NetworkConfig(context).buildApi().signup(registerRequest)
//                .enqueue(object: Callback<APIResponse<String>> {
//                    override fun onResponse(
//                        call: Call<APIResponse<String>>,
//                        response: Response<APIResponse<String>>
//                    ) {
//                        if (response.body()?.status != HttpsURLConnection.HTTP_OK){
//                            val res = response.body()!!.message
//                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
//                        } else {
//                            val res = response.body()!!.message
//                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
//                            Handler().postDelayed({
//                                val intent = Intent(activity, AuthActivity::class.java)
//                                startActivity(intent)
//                                activity?.finish()
//                            }, 2000)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<APIResponse<String>>, t: Throwable) {
//                        Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
//                    }
//                })
        }
        binding.textLogin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.registerActionLogin)
        }
    }
}