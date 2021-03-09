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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentMembersListBinding
import com.jhiltunen.finnishparliamentmembers.logic.viewmodelfactorys.ParliamentMembersViewModelFactory
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.ParliamentMembersViewModel
import com.jhiltunen.finnishparliamentmembers.ui.adapters.ParliamentMemberListAdapter
import com.jhiltunen.finnishparliamentmembers.ui.adapters.ParliamentMemberListener


class ParliamentMembersFragment : Fragment() {

    private lateinit var binding: FragmentMembersListBinding
    private lateinit var viewModel: ParliamentMembersViewModel
    private lateinit var adapter: ParliamentMemberListAdapter
    private lateinit var viewModelFactory : ParliamentMembersViewModelFactory


    //var party : LiveData<String>() = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: ParliamentMembersFragmentArgs by navArgs()
        val party = args.party

        Log.d("ARGS", ParliamentMembersFragmentArgs.fromBundle(requireArguments()).party)
        //Log.d("parliamentmembers", party)
        //ParliamentMembersFragment.fromBundle(requireArguments()).partyName
        viewModelFactory = ParliamentMembersViewModelFactory(party, requireNotNull(activity).application)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_members_list, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ParliamentMembersViewModel::class.java)

        binding.viewModel = viewModel

        adapter = ParliamentMemberListAdapter(requireContext(), ParliamentMemberListener {hetekaId ->
            Toast.makeText(context, "$hetekaId", Toast.LENGTH_LONG).show()
            viewModel.onParliamentMemberClicked(hetekaId)
        })

        binding.playerView.adapter = adapter
        binding.playerView.layoutManager = LinearLayoutManager(context)

        viewModel.parliamentMembers.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.navigateToMemberDetail.observe(viewLifecycleOwner, Observer { heteka ->
            heteka?.let {
                this.findNavController().navigate(
                    ParliamentMembersFragmentDirections
                        .actionParliamentMembersFragmentToMemberFragment3(heteka))
                Log.d("HETEKA", "$heteka")
                viewModel.onParliamentMemberNavigated()
            }
        })
        return binding.root
    }
}