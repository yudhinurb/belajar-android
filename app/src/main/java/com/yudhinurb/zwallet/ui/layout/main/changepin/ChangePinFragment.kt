package com.yudhinurb.zwallet.ui.layout.main.changepin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentChangePinBinding
import com.yudhinurb.zwallet.databinding.FragmentPersonalInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePinFragment : Fragment() {
    private lateinit var binding: FragmentChangePinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnLogin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_changePinFragment_to_newPinFragment)
        }
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}