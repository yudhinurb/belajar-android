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
import com.yudhinurb.zwallet.databinding.ItemContactFindreceiverBinding
import com.yudhinurb.zwallet.model.AllContacts
import com.yudhinurb.zwallet.utils.BASE_URL

class ContactAdapter(
    private var data: List<AllContacts>,
    private var clickListener: (AllContacts, View) -> Unit,
): RecyclerView.Adapter<ContactAdapter.ContactAdapterHolder>() {
    private lateinit var contextAdapter: Context

    class ContactAdapterHolder(private val binding: ItemContactFindreceiverBinding): RecyclerView.ViewHolder(binding.root) {
        private val image: ShapeableImageView = binding.imageContact
        private val name: TextView = binding.contactName
        private val phone: TextView = binding.contactNumber

        fun bindData(data: AllContacts, onClick: (AllContacts, View) -> Unit){
            Glide.with(image).load(BASE_URL +data.image).apply(
                RequestOptions.circleCropTransform().placeholder(R.drawable.rumbling)
            ).into(image)
            name.text = data.name.toString()
            phone.text = data.phone.toString()

            binding.root.setOnClickListener{
                onClick(data, binding.root)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactAdapter.ContactAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context
        val binding = ItemContactFindreceiverBinding.inflate(inflater, parent, false)
        return ContactAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactAdapterHolder, position: Int) {
        holder.bindData(this.data[position], clickListener)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<AllContacts>){
        this.data = data
    }
}