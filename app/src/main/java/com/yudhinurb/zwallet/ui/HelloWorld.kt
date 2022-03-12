package com.yudhinurb.zwallet.ui
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.yudhinurb.zwallet.R
//import com.yudhinurb.zwallet.adapter.TransactionAdapter
//import com.yudhinurb.zwallet.data.Transaction
//
//class HelloWorld : AppCompatActivity() {
//    private val transactionData = mutableListOf<Transaction>()
//    lateinit var transactionAdapter: TransactionAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_hello_world)
//        this.transactionAdapter = TransactionAdapter(transactionData)
//        val recycler: RecyclerView = findViewById(R.id.recyclerTransaction)
//        val layoutManager = LinearLayoutManager(applicationContext)
//        recycler.layoutManager = layoutManager
//        recycler.adapter = transactionAdapter
//        prepareData()
//    }
//
//    fun prepareData(){
//        this.transactionData.add(Transaction(
//            transactionImage = getDrawable(R.drawable.rumbling)!!,
//            transactionName = "Yudhi Nur",
//            transactionType = "Transfer",
//            transactionNominal = 125000.00
//        ))
//
//        this.transactionAdapter.notifyDataSetChanged()
//    }
//}