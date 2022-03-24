package com.yudhinurb.zwallet.ui.layout.main.changepin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    var otp  = mutableListOf<EditText>()

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

        initEditText()

        binding.etOtp6.addTextChangedListener {
            if (!binding.etOtp6.text.isNullOrEmpty()) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_login_active)
                binding.btnLogin.setTextColor(Color.parseColor("#FFFFFF"))
            } else if (binding.etOtp6.text.isNullOrEmpty()) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_login)
                binding.btnLogin.setTextColor(Color.parseColor("#9DA6B5"))
            }
        }

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

            if (pin.length < 6) {
                Toast.makeText(context, "Lengkapi PIN", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

    fun initEditText(){
        otp.add(0,binding.etOtp1)
        otp.add(1,binding.etOtp2)
        otp.add(2,binding.etOtp3)
        otp.add(3,binding.etOtp4)
        otp.add(4,binding.etOtp5)
        otp.add(5,binding.etOtp6)
        otpHandler()
    }

    fun otpHandler() {
        for (i in 0..5) { //Its designed for 6 digit OTP
            otp.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    otp.get(i).setBackgroundResource(R.drawable.background_input_otp_filled)

                }

                override fun afterTextChanged(s: Editable) {
                    if (i == 5 && !otp.get(i).getText().toString().isEmpty()) {
                        otp.get(i).clearFocus()

                        //Clears focus when you have entered the last digit of the OTP.
                    } else if (!otp.get(i).getText().toString().isEmpty()) {
                        otp.get(i + 1)
                            .requestFocus() //focuses on the next edittext after a digit is entered.
                    }
                }
            })
            otp.get(i).setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action !== KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                }
                if (keyCode == KeyEvent.KEYCODE_DEL && otp.get(i).getText().toString()
                        .isEmpty() && i != 0
                ) {
                    //this condition is to handel the delete input by users.
                    otp.get(i - 1).setText("") //Deletes the digit of OTP
                    otp.get(i - 1).requestFocus()
                    otp.get(i).setBackgroundResource(R.drawable.background_input_otp)

                    //and sets the focus on previous digit
                }
                false
            })

        }

    }
}