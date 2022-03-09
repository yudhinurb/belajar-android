package com.yudhinurb.zwallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.adapter.TransactionAdapter
import com.yudhinurb.zwallet.data.Transaction
import com.yudhinurb.zwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.transactionAdapter = TransactionAdapter(transactionData)
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()
    }

    fun prepareData(){
        this.transactionData.add(
            Transaction(
            transactionImage = getDrawable(R.drawable.rumbling)!!,
            transactionName = "Yudhi Nur",
            transactionType = "Transfer",
            transactionNominal = 125000.00
        )
        )
        this.transactionData.add(
            Transaction(
            transactionImage = getDrawable(R.drawable.rumbling)!!,
            transactionName = "Yudhi Nur",
            transactionType = "Transfer",
            transactionNominal = 125000.00
        )
        )

        this.transactionAdapter.notifyDataSetChanged()
    }
}