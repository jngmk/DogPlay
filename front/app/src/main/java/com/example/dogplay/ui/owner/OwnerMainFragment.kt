package com.example.dogplay.ui.owner

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dogplay.R
import kotlinx.android.synthetic.main.fragment_owner_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [OwnerMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OwnerMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnEnrollHotel.setOnClickListener {
            val intent = Intent(this.context, OwnerEnrollHotel::class.java)
            startActivity(intent)
        }

        btnEnrollHotelRoom.setOnClickListener {
            val intent = Intent(this.context, OwnerEnrollHotelRoom::class.java)
            startActivity(intent)
        }
    }

}
