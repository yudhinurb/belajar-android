package com.yudhinurb.zwallet.ui.layout.main.findreceiver

import android.content.SharedPreferences
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentFindReceiverBinding
import com.yudhinurb.zwallet.ui.adapter.ContactAdapter
import com.yudhinurb.zwallet.ui.widget.LoadingDialog
import com.yudhinurb.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class FindReceiverFragment : Fragment() {
    private lateinit var binding: FragmentFindReceiverBinding
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var prefs: SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: ContactViewModel by activityViewModels()

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

        prepareData()

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.textContacts.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_findReceiverFragment_to_inputAmountFragment)
        }
    }

    fun prepareData() {
        this.contactAdapter = ContactAdapter(listOf())

        binding.recyclerContact.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }

        viewModel.getAllContacts().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                    binding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        recyclerContact.visibility = View.GONE
                    }
                }
                State.SUCCESS -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerContact.visibility = View.VISIBLE
                    }
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.contactAdapter.apply {
                            addData(it.resource.data!!)
                            notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                State.ERROR -> {
                    binding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        recyclerContact.visibility = View.GONE
                    }
                }
            }
        }
    }
}