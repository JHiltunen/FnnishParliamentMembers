package com.jhiltunen.finnishparliamentmembers.logic.viewmodelfactorys

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.ParliamentMembersViewModel
import com.jhiltunen.finnishparliamentmembers.logic.viewmodels.PartiesViewModel

class ParliamentMembersViewModelFactory(private val party: String, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParliamentMembersViewModel::class.java)) {
            return ParliamentMembersViewModel(party, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}