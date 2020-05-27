package com.example.dogplay

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chat_list.view.*
import kotlinx.android.synthetic.main.chat_page.*
import kotlinx.android.synthetic.main.chat_page.view.*

/**
 * A simple [Fragment] subclass.
 */
class chatPage : Fragment() {

//    companion object{
//        fun newInstance() = chatPage()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        chatrv.layoutManager = layoutManager
        val adapter = ChatAdapter(this.requireContext(), Supplier.chattings)
        chatrv.adapter = adapter
    }
}

class ChatAdapter(var context: Context, var chattings:List<Chatting>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_list ,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chattings.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chattings[position]
        holder.itemView.chatNic.text = chat.id
        holder.itemView.chatTop.text = chat.chats[0]
    }
}
