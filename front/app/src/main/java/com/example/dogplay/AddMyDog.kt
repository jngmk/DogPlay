package com.example.dogplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_add_my_dog.*

class AddMyDog : AppCompatActivity() {
    private lateinit var dogFragment: AddMyDogList
    private lateinit var addDogFragment: AddMyDogInfo
    private lateinit var editDogFragment: AddMyDogEdit
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_my_dog)

        dogFragment = AddMyDogList.newInstance()
        addDogFragment = AddMyDogInfo.newInstance()
        editDogFragment = AddMyDogEdit.newInstance()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, dogFragment)
                .commitNow()
            activeFragment = dogFragment
        }
    }

    internal fun onOpenAddDog() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, dogFragment)
            .commitNow()
    }

    internal fun onOpenAddDogInfo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, addDogFragment)
            .commitNow()
    }

    internal fun onOpenEditDog() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, editDogFragment)
            .commitNow()
    }
}
