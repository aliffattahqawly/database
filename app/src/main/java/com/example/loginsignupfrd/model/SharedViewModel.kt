package com.example.loginsignupfrd.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginsignupfrd.model.Item

class SharedViewModel : ViewModel() {

    private val _historyItems = MutableLiveData<MutableList<Item>>()
    val historyItems: LiveData<MutableList<Item>> get() = _historyItems

    private val _favoriteItems = MutableLiveData<MutableList<Item>>()
    val favoriteItems: LiveData<MutableList<Item>> get() = _favoriteItems

    init {
        _historyItems.value = mutableListOf()
        _favoriteItems.value = mutableListOf()
    }

    fun addToHistory(item: Item) {
        _historyItems.value?.add(item)
        _historyItems.value = _historyItems.value // Trigger LiveData update
    }

    fun addToFavorite(item: Item) {
        val currentList = _favoriteItems.value ?: mutableListOf()
        if (!currentList.contains(item)) {
            currentList.add(item)
            _favoriteItems.value = currentList
        }
    }

    fun removeFromFavorite(item: Item) {
        val currentList = _favoriteItems.value ?: mutableListOf()
        currentList.remove(item)
        _favoriteItems.value = currentList
    }
}