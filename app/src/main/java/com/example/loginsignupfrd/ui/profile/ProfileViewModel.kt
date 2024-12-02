package com.example.loginsignupfrd.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }
    val text: LiveData<String> = _text

    private val _profileDescription = MutableLiveData<String>().apply {
        value = "Universitas Sirivijaya\n\nPALEMBANG"
    }
    val profileDescription: LiveData<String> = _profileDescription
}