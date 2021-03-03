package com.jhiltunen.finnishparliamentmembers.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.ParliamentMemberViewModel
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.databinding.ActivityMainBinding
import com.jhiltunen.finnishparliamentmembers.logic.models.Parliament

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ParliamentMemberViewModel
    private lateinit var adapter: ParliamentMemberListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ParliamentMemberViewModel::class.java)
        binding.viewModel = viewModel

        adapter = ParliamentMemberListAdapter(this)
        binding.playerView.adapter = adapter
        binding.playerView.layoutManager = LinearLayoutManager(this)

        viewModel.parliamentMembers.observe(this) {
            Log.d("HURU", it[0].lastname)
            it.stream().forEach { member -> viewModel.insertMemberToDatabase(member) }
            adapter.submitList(it)
        }
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