package com.example.dogplay

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.chat_list.*
import kotlinx.android.synthetic.main.chat_page.*
import kotlinx.android.synthetic.main.chat_send.view.*
import kotlinx.android.synthetic.main.direct_message.*
import kotlinx.coroutines.internal.artificialFrame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class DirectMessage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.direct_message)
        val server = server()
        val target = intent.getStringExtra("target")
        sendBtn.setOnClickListener{
            if (sendMessage.text.isEmpty()){
                Log.d("비었다","하하")
            }
            else {
                Log.d("뭐라 친건데", sendMessage.text.toString())
                server!!.PostChatInsert(ChatInsert(1,"", 0,sendMessage.text.toString(),"사진같음",0,"owner1","test1")).enqueue(object :Callback<DMSend>{
                    override fun onFailure(call: Call<DMSend>, t: Throwable) {
                        Log.d("말썽이네", t.toString())
                    }

                    override fun onResponse(call: Call<DMSend>, response: Response<DMSend>) {
                        Log.d("될줄 알았어", response.body().toString())
                        val layoutInflater = getLayoutInflater()
                        val chatview = layoutInflater.inflate(R.layout.chat_send,null)
                        Supplier.DMList.add(DMset("test1","owner1",sendMessage.text.toString(),
                            response.body()!!.data,0,"없어 사진",0))
                        sendMessage.setText("")
                        chatRecycler.adapter!!.notifyDataSetChanged()
                        chatRecycler.scrollToPosition(chatRecycler.adapter!!.itemCount-1)
                    }
                })
            }
        }
        server!!.chatTwoPeople("test1",target).enqueue(object : Callback<DMDTO>{
            override fun onFailure(call: Call<DMDTO>, t: Throwable) {
                Log.d("또안됐어?", t.toString())
            }

            override fun onResponse(call: Call<DMDTO>, response: Response<DMDTO>) {
                var DMList = ArrayList<DMset>()
                Log.d("채팅내역", response.body().toString())
                for(chat in response.body()!!.data){
                    var viewType:Int
                    if (chat.receive == "test1") viewType = 1
                    else viewType = 0
                    DMList.add(DMset(chat.receive, chat.send, chat.message, chat.created, chat.readmessage, chat.picture,viewType))
                    server.ChatUpdate(ChatIn(chat.id, chat.chatid,chat.receive,chat.send,chat.picture,chat.message,chat.created,1)).enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        }
                    })
                }
                Supplier.DMList = DMList
                val layoutManager = LinearLayoutManager(applicationContext)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                chatRecycler.layoutManager = layoutManager
                val adapter = DMAdapter(applicationContext, Supplier.DMList)
                chatRecycler.adapter = adapter
                chatRecycler.scrollToPosition(adapter.itemCount-1)
            }
        })
    }
}
class DMAdapter(var context: Context, var DMs:ArrayList<DMset>) :
    RecyclerView.Adapter<DMAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("viewType", parent.context.toString())
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        var view:View
        if (viewType == 0){
            view = LayoutInflater.from(context).inflate(R.layout.chat_send ,parent, false)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_receieve ,parent, false)
        }

        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return DMs[position].viewType
    }

    override fun getItemCount(): Int {
        return DMs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val DM = DMs[position]
        val target:String
        var half:String
        var hour:Int
        holder.itemView.msg.text = DM.message
        if (DM.created[3] > 12) {half = "오후"; hour = DM.created[3]-12}
        else if (DM.created[3] == 12) {half = "오후"; hour = 12}
        else {half = "오전"; hour = DM.created[3]}
        var min:String
        if(DM.created[4] < 10) {
            min = "0${DM.created[4]}"
        } else {
            min = "${DM.created[4]}"
        }
        holder.itemView.messageAt.text = "${half} ${hour}:${DM.created[4]}"
        holder.itemView.messageAt.text = "${DM.created[0]}-${DM.created[1]}-${DM.created[2]} ${DM.created[3]}:${min}"
    }
}
