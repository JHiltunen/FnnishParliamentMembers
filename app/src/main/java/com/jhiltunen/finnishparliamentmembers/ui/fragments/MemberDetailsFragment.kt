package com.jhiltunen.finnishparliamentmembers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.bindImage
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentMemberViewDetailsBinding
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.ParliamentMemberDetailsViewModel
import com.jhiltunen.finnishparliamentmembers.logic.viewmodelfactorys.ParliamentMemberDetailsViewModelFactory

class MemberDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMemberViewDetailsBinding
    private lateinit var viewModel: ParliamentMemberDetailsViewModel
    private lateinit var viewModelFactory : ParliamentMemberDetailsViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val args: MemberDetailsFragmentArgs by navArgs()
        val hetekaId = args.hetekaId

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_view_details,container,false)

        viewModelFactory = ParliamentMemberDetailsViewModelFactory(hetekaId, requireNotNull(activity).application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ParliamentMemberDetailsViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.member.observe(viewLifecycleOwner, Observer {
            binding.firstname.text = it.firstname
            binding.lastname.text = it.lastname
            binding.partyName.text = it.party
            bindImage(binding.imageView, "https://avoindata.eduskunta.fi/${it.pictureUrl}")
        })

        viewModel.memberLikes.observe(viewLifecycleOwner, Observer {
           binding.likeDislikeRatio.text = it.toString()
        })

        binding.likeButton.setOnClickListener {
            viewModel.increaseLikes()
        }

        binding.dislikeButton.setOnClickListener {
            viewModel.decreaseLikes()
        }

        return binding.root
    }
}