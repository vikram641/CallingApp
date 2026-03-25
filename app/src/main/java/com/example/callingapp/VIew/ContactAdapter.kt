package com.example.callingapp.VIew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callingapp.databinding.ItemContactBinding

import com.example.callingapp.model.Contact

class ContactAdapter(
    private val list: List<Contact>,
    private val onCallClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = list[position]

        holder.binding.tvName.text = contact.name
        holder.binding.tvNumber.text = contact.number

        holder.binding.btnCall.setOnClickListener {
            onCallClick(contact)
        }

        holder.itemView.setOnClickListener {
            onCallClick(contact)
        }
    }
}