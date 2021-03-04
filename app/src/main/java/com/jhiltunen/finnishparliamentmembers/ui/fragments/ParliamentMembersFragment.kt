package com.jhiltunen.finnishparliamentmembers.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.ParliamentMembersFragmentBinding
import com.jhiltunen.finnishparliamentmembers.ui.adapters.ParliamentMemberListAdapter


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