package com.jhiltunen.finnishparliamentmembers.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentPartiesBinding
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.PartiesViewModel
import com.jhiltunen.finnishparliamentmembers.ui.adapters.PartiesListAdapter
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentPartiesListBinding
import com.jhiltunen.finnishparliamentmembers.ui.adapters.PartiesListener

/**
 * A fragment representing a list of Items.
 */
class PartiesFragment : Fragment() {

    private lateinit var binding: FragmentPartiesListBinding
    private lateinit var viewModel: PartiesViewModel
    private lateinit var adapter: PartiesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_parties_list, container, false)

        viewModel = ViewModelProvider(this).get(PartiesViewModel::class.java)
        binding.viewModel = viewModel

        adapter = PartiesListAdapter(requireContext(), viewModel.parties)

        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(context, 4)

        viewModel.parties.observe(viewLifecycleOwner) {
            Log.d("LISTA", "$it")
            adapter.submitList(it)
        }

        viewModel.navigateToPartyMemberList.observe(viewLifecycleOwner, Observer { partyName ->
           // Toast.makeText(context, "Party: $partyName", Toast.LENGTH_LONG)

            partyName?.let {
                this.findNavController().navigate(
                    PartiesFragmentDirections.actionPartiesFragmentToParliamentMembersFragment(it))
                Log.d("PARTY", "$partyName")
                viewModel.onPartyMemberListNavigated()
            }
        })
        return binding.root
    }
}