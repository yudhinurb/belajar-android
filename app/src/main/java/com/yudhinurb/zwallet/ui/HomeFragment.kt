package com.yudhinurb.zwallet.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.adapter.TransactionAdapter
import com.yudhinurb.zwallet.data.Transaction
import com.yudhinurb.zwallet.data.api.ZWalletApi
import com.yudhinurb.zwallet.databinding.FragmentHomeBinding
import com.yudhinurb.zwallet.model.APIResponse
import com.yudhinurb.zwallet.model.Balance
import com.yudhinurb.zwallet.model.User
import com.yudhinurb.zwallet.network.NetworkConfig
import com.yudhinurb.zwallet.utils.KEY_LOGGED_IN
import com.yudhinurb.zwallet.utils.PREFS_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences
//    private lateinit var apiClient: ZWalletApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        apiClient = NetworkConfig(context).buildApi()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

//        val userBalance = apiClient.getBalance().execute()
//        if (userBalance.isSuccessful)
//            if (userBalance.body()?.status != HttpsURLConnection.HTTP_OK) {
//                Toast.makeText(context, "Fetch detail data failed", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                val res = userBalance.body()?.data?.get(0)
//                binding.textName.text = res?.name
//                binding.textPhoneNumber.text = res?.phone
//            }

        this.transactionAdapter = TransactionAdapter(transactionData)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter

        prepareData()

        NetworkConfig(context).getService().getBalance()
            .enqueue(object: Callback<APIResponse<List<Balance>>> {
                override fun onResponse(
                    call: Call<APIResponse<List<Balance>>>,
                    response: Response<APIResponse<List<Balance>>>
                ) {
                    if (response.body()?.status != HttpURLConnection.HTTP_OK){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    } else {
                        val res = response.body()?.data?.get(0)

                        binding.textName.text = res?.name
                        binding.textPhoneNumber.text = res?.phone.toString()
                        binding.textBalanceAmount.text = res?.balance.toString()
                    }
                }

                override fun onFailure(call: Call<APIResponse<List<Balance>>>, t: Throwable) {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }

            })

        binding.imageProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }
    }
    fun prepareData(){
        this.transactionData.add(
            Transaction(
                transactionImage = activity?.getDrawable(R.drawable.rumbling)!!,
                transactionName = "Yudhi Nur",
                transactionType = "Transfer",
                transactionNominal = 125000.00
            )
        )
        this.transactionData.add(
            Transaction(
                transactionImage = activity?.getDrawable(R.drawable.rumbling)!!,
                transactionName = "Yudhi Nur",
                transactionType = "Transfer",
                transactionNominal = 125000.00
            )
        )

        this.transactionAdapter.notifyDataSetChanged()
    }
}