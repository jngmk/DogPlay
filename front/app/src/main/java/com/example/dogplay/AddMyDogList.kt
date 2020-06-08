package com.example.dogplay

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_add_my_dog_list.*

class AddMyDogList : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private val dogs = Supplier.dogs

    companion object {
        fun newInstance() = AddMyDogList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_my_dog_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        MutableSupplier.dogs.observe(viewLifecycleOwner, Observer {
            dogs ->
            mRecyclerView.adapter!!.notifyDataSetChanged()
            Supplier.dogs = dogs
        })

        mRecyclerView = rcvAddMyDog

        btnBack.setOnClickListener {
            (activity as AddMyDog).finish()
        }
        btnAdd.setOnClickListener {
            (activity as AddMyDog).onOpenAddDogInfo()
        }

        mRecyclerView.adapter = RecyclerAdapter(dogs)
        mRecyclerView.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL,false)
//        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
//        mRecyclerView.layoutParams = layoutParams
    }

    inner class RecyclerAdapter(private val dogs: ArrayList<DogInfo>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.add_my_dog_item_with_name, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val dog = dogs[position]
            holder.updatedogs(dog)

            val img: ImageView = holder.itemView.findViewById(R.id.imgDog)
            img.setOnClickListener {
                Supplier.dogEdit = dog
                (activity as AddMyDog).onOpenEditDog()
            }
        }

        override fun getItemCount(): Int = dogs.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var img: ImageView = itemView.findViewById(R.id.imgDog)
        private var name: TextView = itemView.findViewById(R.id.txtMyDogName)

        fun updatedogs(dog: DogInfo) {
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
