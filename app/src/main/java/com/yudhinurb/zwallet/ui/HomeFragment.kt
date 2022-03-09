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
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.adapter.TransactionAdapter
import com.yudhinurb.zwallet.data.Transaction
import com.yudhinurb.zwallet.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.transactionAdapter = TransactionAdapter(transactionData)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()

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