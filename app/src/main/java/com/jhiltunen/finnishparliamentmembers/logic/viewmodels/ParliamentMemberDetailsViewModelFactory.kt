package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ParliamentMemberDetailsViewModelFactory(private val hetekaId: Int, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParliamentMemberDetailsViewModel::class.java)) {
            return ParliamentMemberDetailsViewModel(hetekaId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}