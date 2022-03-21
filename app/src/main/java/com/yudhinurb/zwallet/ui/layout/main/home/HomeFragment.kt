package com.yudhinurb.zwallet.ui.layout.main.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.ui.adapter.TransactionAdapter
import com.yudhinurb.zwallet.data.Transaction
import com.yudhinurb.zwallet.databinding.FragmentHomeBinding
import com.yudhinurb.zwallet.ui.layout.viewModelsFactory
import com.yudhinurb.zwallet.utils.BASE_URL
import com.yudhinurb.zwallet.utils.Helper.formatPrice
import com.yudhinurb.zwallet.utils.PREFS_NAME
import com.yudhinurb.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        apiClient = NetworkConfig(context).buildApi()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        prepareData()

        binding.imageProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }

        binding.buttonTopUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_topupFragment)
        }

        binding.buttonTransfer.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_findReceiverFragment)
        }
    }

    fun prepareData(){
        this.transactionAdapter = TransactionAdapter(listOf())

        binding.recyclerTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                binding.apply {
                    textBalanceAmount.formatPrice(it.resource.data?.get(0)?.balance.toString())
                    textPhoneNumber.text = it.resource.data?.get(0)?.phone
                    textName.text = it.resource.data?.get(0)?.name
                    Glide.with(imageProfile).load(BASE_URL +it.resource.data?.get(0)?.image).apply(
                        RequestOptions.circleCropTransform().placeholder(R.drawable.rumbling)
                    ).into(imageProfile)
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getInvoice().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                    binding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        recyclerTransaction.visibility = View.GONE
                    }
                }
                State.SUCCESS -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerTransaction.visibility = View.VISIBLE
                    }
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.transactionAdapter.apply {
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
                        recyclerTransaction.visibility = View.GONE
                    }
                }
            }
        }
    }
}