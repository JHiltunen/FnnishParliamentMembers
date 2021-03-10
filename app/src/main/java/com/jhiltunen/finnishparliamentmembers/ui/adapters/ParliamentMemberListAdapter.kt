package com.jhiltunen.finnishparliamentmembers.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentMemberListItemBinding
import com.jhiltunen.finnishparliamentmembers.ui.fragments.ParliamentMembersFragmentDirections

class ParliamentMemberListAdapter(private val context: Context, val membersList: LiveData<List<ParliamentMember>>): ListAdapter<ParliamentMember, ParliamentMemberListAdapter.ViewHolder>(ParliamentMemberDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.fragment_member_list_item, parent, false)
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ParliamentMemberListAdapter.ViewHolder, position: Int) {
        (holder.binding.listItem).apply {
            val item = membersList.value?.get(position)
            holder.binding.firstName.text = item?.firstname ?: "unknown"
            holder.binding.lastName.text = item?.lastname ?: "unknown"
            bindImage(holder.binding.memberImage, "https://avoindata.eduskunta.fi/${item?.pictureUrl}")
            setOnClickListener {
                val action =
                        item?.hetekaId?.let { it1 -> ParliamentMembersFragmentDirections.actionParliamentMembersFragmentToMemberFragment3(it1) }
                if (action != null) {
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    class ViewHolder private constructor(val binding: FragmentMemberListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentMemberListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ParliamentMemberDiffCallback: DiffUtil.ItemCallback<ParliamentMember>() {
    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
}