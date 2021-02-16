package com.jhiltunen.finnishparliamentmembers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.oop_projekti1.Parliament
import com.example.oop_projekti1.ParliamentMembersData
import com.jhiltunen.finnishparliamentmembers.databinding.FragmentMemberBinding

class MemberFragment : Fragment() {

    private lateinit var binding: FragmentMemberBinding
    private lateinit var parliament: Parliament
    private var memberIndex: Int = 0
    private var likeDislikeRatio = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentMemberBinding>(inflater,
            R.layout.fragment_member,container,false)

        parliament = Parliament(ParliamentMembersData.members)

        updateUi()

        binding.likeButton.setOnClickListener {
            likeDislikeRatio++
            updateUi()
        }

        binding.dislikeButton.setOnClickListener {
            likeDislikeRatio--
            updateUi()
        }

        binding.randomMemberButton.setOnClickListener {
            pickRandomIndex()
            updateUi()
        }

        binding.nextMember.setOnClickListener {
            if (memberIndex == parliament.members.size - 1) {
                memberIndex = 0
            } else {
                memberIndex++
            }
            updateUi()
        }

        binding.previousMember.setOnClickListener {
            if (memberIndex == 0) {
                memberIndex = parliament.members.size - 1
            } else {
                memberIndex--
            }
            updateUi()
        }

        return binding.root
    }

    private fun updateUi() {
        binding.firstname.text = parliament.members[memberIndex].firstname
        binding.lastname.text = parliament.members[memberIndex].lastname
        binding.parliament.text = parliament.members[memberIndex].party
        binding.likeDislikeRatio.text = likeDislikeRatio.toString()
    }

    private fun pickRandomIndex() {
        memberIndex = (0 until parliament.members.size - 1).random()
    }
}