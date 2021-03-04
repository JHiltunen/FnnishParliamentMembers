package com.jhiltunen.finnishparliamentmembers.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember

class ParliamentMemberListAdapter(private val context: Context): ListAdapter<ParliamentMember, ParliamentMemberViewHolder>(
    PlayerDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParliamentMemberViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.player_item_view, parent, false)
        return ParliamentMemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ParliamentMemberViewHolder, position: Int) {
        // could use binding?
        holder.itemView.findViewById<TextView>(R.id.playername).text = getItem(position).firstname
        holder.itemView.findViewById<TextView>(R.id.playerteam).text = getItem(position).lastname
    }
}

class ParliamentMemberViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class PlayerDiffCallback: DiffUtil.ItemCallback<ParliamentMember>() {
    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
}