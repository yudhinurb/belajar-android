package com.yudhinurb.zwallet.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.model.AllContacts
import com.yudhinurb.zwallet.utils.BASE_URL

class ContactAdapter(private var data: List<AllContacts>): RecyclerView.Adapter<ContactAdapter.ContactAdapterHolder>() {
    private lateinit var contextAdapter: Context

    class ContactAdapterHolder(view: View): RecyclerView.ViewHolder(view) {
        private val image: ShapeableImageView = view.findViewById(R.id.imageContact)
        private val name: TextView = view.findViewById(R.id.contactName)
        private val phone: TextView = view.findViewById(R.id.contactNumber)

        fun bindData(data: AllContacts, context: Context, position: Int){
            Glide.with(image).load(BASE_URL +data.image).apply(
                RequestOptions.circleCropTransform().placeholder(R.drawable.rumbling)
            ).into(image)
            name.text = data.name.toString()
            phone.text = data.phone.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactAdapter.ContactAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context
        val inflatedView: View = inflater.inflate(R.layout.item_contact_findreceiver, parent, false)
        return ContactAdapterHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactAdapterHolder, position: Int) {
        holder.bindData(this.data[position], contextAdapter, position)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<AllContacts>){
        this.data = data
    }
}