package com.jhiltunen.finnishparliamentmembers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.ParliamentMemberViewModel
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ParliamentMemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ParliamentMemberViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.parliamentMembers.observe(this) {
            Log.d("PARLIAMENT", it[0].lastname)
        }

    }
}