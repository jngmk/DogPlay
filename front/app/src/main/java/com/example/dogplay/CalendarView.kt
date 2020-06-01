package com.example.dogplay

import android.content.ContentValues
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.CalendarContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dogplay.CalendarView
import kotlinx.android.synthetic.main.calendar.*
import java.util.*
import java.util.Calendar

class CalendarView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)

        val calID: Long = 3
        val startMillis: Long = Calendar.getInstance().run{
            set(2019,9,14,7,30)
            timeInMillis
        }
        val endMillis: Long = Calendar.getInstance().run{
            set(2019,9,15,8,30)
            timeInMillis
        }
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
        }
    }
}