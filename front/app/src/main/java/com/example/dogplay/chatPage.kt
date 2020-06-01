package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chat_list.view.*
import kotlinx.android.synthetic.main.chat_page.*
import kotlinx.android.synthetic.main.chat_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val server = server()
        server!!.searchChatWithUserId("test1").enqueue(object :Callback<ChatMainDTO>{
            override fun onFailure(call: Call<ChatMainDTO>, t: Throwable) {
                Log.d("왜 안될까잉?", t.toString())
            }

            override fun onResponse(call: Call<ChatMainDTO>, response: Response<ChatMainDTO>) {
                Log.d("테스트 채팅 정보", response.body().toString())
                var chatData = response.body()!!.data
                val layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                chatrv.layoutManager = layoutManager
                val adapter = ChatAdapter(requireContext(), chatData)
                chatrv.adapter = adapter
            }
        })

    }
}

class ChatAdapter(var context: Context, var chattings:ArrayList<ChatMain>) :
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
        val chat = chattings[position].chat
        val target:String
        if (chat.receive != "test1") target = chat.receive
        else target = chat.send
        holder.itemView.chatNic.text = target
        holder.itemView.chatTop.text = chat.message
        holder.itemView.messageAt.text = "${chat.created[0]}-${chat.created[1]}-${chat.created[2]} ${chat.created[3]}:${chat.created[4]}"
        holder.itemView.setOnClickListener{
            val intent = Intent(context, DirectMessage::class.java)
            intent.putExtra("target",target)
            context.startActivity(intent)
        }
    }
}
