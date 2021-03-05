package com.jhiltunen.finnishparliamentmembers.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.databinding.PlayerItemViewBinding

class ParliamentMemberListener(val clickListener: (memberId: Int) -> Unit) {
    fun onClick(member: ParliamentMember) = clickListener(member.hetekaId)
}

class ParliamentMemberListAdapter(private val context: Context, val clickListener: ParliamentMemberListener): ListAdapter<ParliamentMember, ParliamentMemberListAdapter.ViewHolder>(PlayerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.player_item_view, parent, false)
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // could use binding?
        holder.itemView.findViewById<TextView>(R.id.playername).text = getItem(position).firstname
        holder.itemView.findViewById<TextView>(R.id.playerteam).text = getItem(position).lastname

        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: PlayerItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParliamentMember, clickListener: ParliamentMemberListener) {
            binding.member = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlayerItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PlayerDiffCallback: DiffUtil.ItemCallback<ParliamentMember>() {
    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
}