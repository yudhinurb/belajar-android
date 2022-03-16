package com.yudhinurb.zwallet.ui.layout.main.transfersuccess

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.yudhinurb.zwallet.databinding.FragmentTransferSuccessBinding
import com.yudhinurb.zwallet.ui.layout.main.MainActivity


class TransferSuccessFragment : Fragment() {
    private lateinit var binding: FragmentTransferSuccessBinding


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
    }


}