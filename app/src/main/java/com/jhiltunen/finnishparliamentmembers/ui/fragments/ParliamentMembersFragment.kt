package com.jhiltunen.finnishparliamentmembers.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.ParliamentMembersFragmentBinding
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.ParliamentMembersViewModel
import com.jhiltunen.finnishparliamentmembers.ui.adapters.ParliamentMemberListAdapter
import com.jhiltunen.finnishparliamentmembers.ui.adapters.ParliamentMemberListener


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

        adapter = ParliamentMemberListAdapter(requireContext(), ParliamentMemberListener {hetekaId ->
            Toast.makeText(context, "$hetekaId", Toast.LENGTH_LONG).show()
            viewModel.onParliamentMemberClicked(hetekaId)
        })

        binding.playerView.adapter = adapter
        binding.playerView.layoutManager = LinearLayoutManager(context)

        viewModel.parliamentMembers.observe(viewLifecycleOwner) {
            Log.d("***", "Observing")
            //Log.d("HURU", it[0].lastname)
            viewModel.insertAllMembersToDatabase(it)
            adapter.submitList(it)
        }

        viewModel.navigateToMemberDetail.observe(viewLifecycleOwner, Observer { heteka ->
            heteka?.let {
                if (null != it) {
                    this.findNavController().navigate(
                        ParliamentMembersFragmentDirections
                            .actionParliamentMembersFragmentToMemberFragment3(heteka))
                    Log.d("HETEKA", "$heteka")
                    viewModel.onParliamentMemberNavigated()
                }
            }
        })
        return binding.root
    }
}