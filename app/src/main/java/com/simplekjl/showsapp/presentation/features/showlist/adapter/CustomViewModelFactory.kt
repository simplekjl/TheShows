package com.simplekjl.showsapp.presentation.features.showlist.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simplekjl.showsapp.domain.Repository
import com.simplekjl.showsapp.presentation.features.showlist.ShowListActivityViewModel
import com.simplekjl.showsapp.presentation.features.showlist.mapper.StateMapper

class CustomViewModelFactory(private val repository: Repository, private val stateMapper: StateMapper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowListActivityViewModel(repository, stateMapper) as T
    }
}