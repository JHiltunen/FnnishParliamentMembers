package com.jhiltunen.finnishparliamentmembers.ui.adapters

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentPartiesBinding
import com.jhiltunen.finnishparliamentmembers.ui.fragments.PartiesFragmentDirections

class PartiesListener(val clickListener: (party: String) -> Unit) {
    fun onClick(party: String) = clickListener(party)
}

class PartiesListAdapter(private val context: Context, val partiesList : LiveData<List<String>>) : ListAdapter<String, PartiesListAdapter.ViewHolder>(PartiesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.fragment_parties, parent, false)
        return ViewHolder.from(parent)
    }

    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // could use binding?
        //holder.itemView.findViewById<TextView>(R.id.partyName).text = getItem(position)


        holder.bind(getItem(position)!!, clickListener)
    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView.findViewById<TextView>(R.id.partyName) as TextView).apply {
            text = partiesList.value?.get(position) ?: "unknown"
            setOnClickListener {
                val action =
                    PartiesFragmentDirections.actionPartiesFragmentToParliamentMembersFragment(text.toString())
                it.findNavController().navigate(action)
            }
        }
    }


    class ViewHolder private constructor(val binding: FragmentPartiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, clickListener: PartiesListener) {
            Log.d("item", item)
            binding.partyName.text = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentPartiesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PartiesDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}