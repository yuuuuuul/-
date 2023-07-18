package com.example.fragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fragment.R



class FragmentViewModel : ViewModel() {
    val data = MutableLiveData<String>()
    val selectItem: LiveData<String> get() = data

    fun setData(newData: String){
        data.value = newData
    }

}