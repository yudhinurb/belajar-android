package com.yudhinurb.zwallet.ui.main.findreceiver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentFindReceiverBinding

class FindReceiverFragment : Fragment() {
    private lateinit var binding: FragmentFindReceiverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFindReceiverBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_findReceiverFragment_to_homeFragment)
        }
    }
}