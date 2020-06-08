package com.example.dogplay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.my_dog_list.*
import kotlin.collections.ArrayList

class MyDogPage:AppCompatActivity() {
    private lateinit var mRecyclerViewTop: RecyclerView
    private lateinit var mRecyclerViewBot: RecyclerView
    private val dogsSelected = Supplier.dogsSelected
    private val dogs = Supplier.dogs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_dog_list)

        mRecyclerViewTop = rcvDogSelected
        mRecyclerViewBot = rcvMyDog

        MutableSupplier.dogsSelected.observe(this, Observer {
                dogs ->
            mRecyclerViewTop.adapter!!.notifyDataSetChanged()
            Supplier.dogsSelected = dogs
        })

        MutableSupplier.dogs.observe(this, Observer {
                dogs ->
            mRecyclerViewBot.adapter!!.notifyDataSetChanged()
            Supplier.dogs = dogs
        })

        mRecyclerViewTop.adapter = RecyclerAdapterTop(dogsSelected)
        mRecyclerViewTop.layoutManager = GridLayoutManager(this, 4, RecyclerView.VERTICAL,false)

        mRecyclerViewBot.adapter = RecyclerAdapterBot(dogs)
        mRecyclerViewBot.layoutManager = GridLayoutManager(this, 4, RecyclerView.VERTICAL,false)


    }

    inner class RecyclerAdapterTop(private val dogs: ArrayList<DogInfo>) : RecyclerView.Adapter<RecyclerViewHolderTop>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolderTop =
            RecyclerViewHolderTop(LayoutInflater.from(parent.context).inflate(R.layout.add_my_dog_item_with_name, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolderTop, position: Int) {
            val dog = dogs[position]
            holder.updateDogsSelected(dog)

            val img: ImageView = holder.itemView.findViewById(R.id.imgDog)
            img.setOnClickListener {
                val count = dogsSelected.size - 1
                for (i in 0..count) {
                    if (dogsSelected[i].id == dog.id) {
                        dogsSelected.removeAt(i)
                        MutableSupplier.dogsSelected.postValue(dogsSelected)
                        break
                    }
                }
            }
        }

        override fun getItemCount(): Int = dogs.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolderTop(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var img: ImageView = itemView.findViewById(R.id.imgDog)
        private var name: TextView = itemView.findViewById(R.id.txtMyDogName)

        fun updateDogsSelected(dog: DogInfo) {
            if (dog.picture != "") {
                Glide.with(itemView)
                    .load(dog.picture)
                    .into(img)
            }
            else {
                img.setImageResource(R.drawable.dog)
            }
            name.text = dog.dogname
        }
    }


    inner class RecyclerAdapterBot(private val dogs: ArrayList<DogInfo>) : RecyclerView.Adapter<RecyclerViewHolderBot>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolderBot =
            RecyclerViewHolderBot(LayoutInflater.from(parent.context).inflate(R.layout.add_my_dog_item_with_name, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolderBot, position: Int) {
            val dog = dogs[position]
            holder.updateDogs(dog)

            val img: ImageView = holder.itemView.findViewById(R.id.imgDog)
            img.setOnClickListener {
                val count = dogsSelected.size - 1
                if (count == -1) {
                    dogsSelected.add(dog)
                }
                else {
                    var flag = true
                    for (i in 0..count) {
                        if (dogsSelected[i].id == dog.id) {
                            dogsSelected[i] = dog
                            flag = false
                            break
                        }
                    }
                    if (flag) dogsSelected.add(dog)
                }
                MutableSupplier.dogsSelected.postValue(dogsSelected)
            }
        }

        override fun getItemCount(): Int = dogs.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolderBot(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var img: ImageView = itemView.findViewById(R.id.imgDog)
        private var name: TextView = itemView.findViewById(R.id.txtMyDogName)

        fun updateDogs(dog: DogInfo) {
            if (dog.picture != "") {
                Glide.with(itemView)
                    .load(dog.picture)
                    .into(img)
            }
            else {
                img.setImageResource(R.drawable.dog)
            }
            name.text = dog.dogname
        }
    }
}



