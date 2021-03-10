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
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentPartiesListItemBinding
import com.jhiltunen.finnishparliamentmembers.ui.fragments.PartiesFragmentDirections

class PartiesListAdapter(private val context: Context, val partiesList : LiveData<List<String>>) : ListAdapter<String, PartiesListAdapter.ViewHolder>(PartiesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.fragment_parties_list_item, parent, false)
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.binding.constraintLayout).apply {

            holder.binding.partyName.text = when(item) {
                "sd" -> "Suomen Sosialidemokraattinen Puolue"
                "ps" -> "Perussuomalaiset"
                "kesk" -> "Suomen Keskusta"
                "kok" -> "Kansallinen Kokoomus"
                "vas" -> "Vasemmistoliitto"
                "vihr" -> "Vihreä liitto"
                "kd" -> "Suomen Kristillisdemokraatit"
                "r" -> "Suomen ruotsalainen kansanpuolue"
                else -> "Liike Nyt"
            }

            holder.binding.imageView2.setImageResource(when(item) {
                "sd" -> R.drawable.sdp
                "ps" -> R.drawable.ps
                "kesk" -> R.drawable.kesk
                "kok" -> R.drawable.kok
                "vas" -> R.drawable.vas
                "vihr" -> R.drawable.vihr
                "kd" -> R.drawable.kd
                "r" -> R.drawable.rkp
                else -> R.drawable.lkn
            })

            setOnClickListener {
                val action =
                    PartiesFragmentDirections.actionPartiesFragmentToParliamentMembersFragment(item)
                it.findNavController().navigate(action)
            }
        }
    }

    class ViewHolder private constructor(val binding: FragmentPartiesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentPartiesListItemBinding.inflate(layoutInflater, parent, false)
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