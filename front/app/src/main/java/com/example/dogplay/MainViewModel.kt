package com.example.dogplay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _user = MutableLiveData<User>()
    private var _dogs = MutableLiveData<ArrayList<DogInfo>>()

    internal var user : MutableLiveData<User>
        get() { return _user}
        set(value) {_user = value}

    internal var dogs : MutableLiveData<ArrayList<DogInfo>>
        get() { return _dogs}
        set(value) {_dogs = value}
}