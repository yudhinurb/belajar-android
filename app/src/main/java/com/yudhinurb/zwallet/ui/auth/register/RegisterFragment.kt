package com.yudhinurb.zwallet.ui.auth.register

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
import androidx.navigation.Navigation
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentRegisterBinding
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.request.RegisterRequest
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.ui.auth.AuthActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            val registerRequest = RegisterRequest(
                binding.inputUsername.text.toString(),
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )
            NetworkConfig(context).buildApi().signup(registerRequest)
                .enqueue(object: Callback<APIResponse<String>> {
                    override fun onResponse(
                        call: Call<APIResponse<String>>,
                        response: Response<APIResponse<String>>
                    ) {
                        if (response.body()?.status != HttpsURLConnection.HTTP_OK){
                            val res = response.body()!!.message
                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                        } else {
                            val res = response.body()!!.message
                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                            Handler().postDelayed({
                                val intent = Intent(activity, AuthActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }, 2000)
                        }
                    }

                    override fun onFailure(call: Call<APIResponse<String>>, t: Throwable) {
                        Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        binding.textLogin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.registerActionLogin)
        }
    }
}