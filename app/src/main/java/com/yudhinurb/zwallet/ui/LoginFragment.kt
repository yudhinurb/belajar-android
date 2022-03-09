package com.yudhinurb.zwallet.ui

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
import androidx.navigation.Navigation
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.editPassword.addTextChangedListener {
            if (binding.editPassword.text.length > 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_login_active)
                binding.btnLogin.setTextColor(Color.parseColor("#FFFFFF"))
            } else if (binding.editPassword.text.length <= 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_login)
                binding.btnLogin.setTextColor(Color.parseColor("#9DA6B5"))
            }
        }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(context, "${binding.editEmail.text} / ${binding.editPassword.text}", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }, 2000)
        }

        binding.textSignUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginActionRegister)
        }

        binding.textForgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginActionForgotPassword)
        }
    }
}