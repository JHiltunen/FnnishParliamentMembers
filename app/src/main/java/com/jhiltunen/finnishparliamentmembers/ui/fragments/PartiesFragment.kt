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
import androidx.recyclerview.widget.GridLayoutManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentPartiesListBinding
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.PartiesViewModel
import com.jhiltunen.finnishparliamentmembers.ui.adapters.PartiesListAdapter

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

        if (viewModel.parties.value == null) {
            viewModel.fetchDataFromApi()
        }

        adapter = PartiesListAdapter(requireContext(), viewModel.parties)

        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(context, 4)

        viewModel.parties.observe(viewLifecycleOwner) {
            Log.d("LISTA", "$it")
            adapter.submitList(it)
        }

        return binding.root
    }
}