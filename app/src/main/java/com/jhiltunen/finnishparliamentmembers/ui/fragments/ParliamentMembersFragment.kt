package com.jhiltunen.finnishparliamentmembers.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.databinding.ParliamentMembersFragmentBinding


class ParliamentMembersFragment : Fragment() {

    private lateinit var binding: ParliamentMembersFragmentBinding
    private lateinit var viewModel: ParliamentMembersViewModel
    private lateinit var adapter: ParliamentMemberListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.parliament_members_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ParliamentMembersViewModel::class.java)
        binding.viewModel = viewModel

        adapter = ParliamentMemberListAdapter(requireContext())
        binding.playerView.adapter = adapter
        binding.playerView.layoutManager = LinearLayoutManager(context)

        viewModel.parliamentMembers.observe(this) {
            Log.d("HURU", it[0].lastname)
            it.stream().forEach { member -> viewModel.insertMemberToDatabase(member) }
            adapter.submitList(it)
        }

        return binding.root
    }
}

class ParliamentMemberListAdapter(private val context: Context): ListAdapter<ParliamentMember, ParliamentMemberViewHolder>(PlayerDiffCallback()) {
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