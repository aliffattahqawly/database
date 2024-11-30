package com.example.loginsignupfrd.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val _historyList = MutableLiveData<List<String>>().apply {
        value = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    }
    val historyList: LiveData<List<String>> = _historyList
}