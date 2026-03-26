package com.example.callingapp.VIew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callingapp.databinding.ItemCallLogBinding
import com.example.callingapp.model.CallLogItem

class CallLogAdapter(
    private val list: List<CallLogItem>,
    private val onCallClick: (CallLogItem) -> Unit
) : RecyclerView.Adapter<CallLogAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCallLogBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCallLogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvName.text = item.name ?: "Unknown"
        holder.binding.tvNumber.text = item.number
        holder.binding.tvType.text = item.type
        holder.binding.tvDate.text = item.date
        holder.binding.tvDuration.text = item.duration

        // Call button click
        holder.binding.btnCall.setOnClickListener {
            onCallClick(item)
        }

        // Whole item click
        holder.itemView.setOnClickListener {
            onCallClick(item)
        }
    }
}