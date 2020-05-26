package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        hotelList.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(this)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        dogList.layoutManager = layoutManager2

//        keyboardListener(MainActivity(), )
        searchBar.setOnFocusChangeListener(object :View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus){
                    footer.setVisibility(View.GONE)
                    Log.d("searchBar", "포커스 얻음")
                } else {
                    footer.visibility = View.VISIBLE
                    Log.d("searchBar","포커스 사라짐")
                }
            }
        })

        toolbar.setOnClickListener{
            searchBar.clearFocus()
        }

        val adapter = HotelAdapter(this, Supplier.hotels)
        hotelList.adapter = adapter

        val adapter2 = DogAdapter(this, Supplier.dogs)
        dogList.adapter = adapter2

        curdate.setOnClickListener{
            val intent = Intent(this, CalendarView::class.java)
            startActivity(intent)
        }
    }
}
