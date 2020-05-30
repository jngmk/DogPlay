package com.example.dogplay

import android.content.Context
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class DirectMessage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.direct_message)
        val server = server()
        val target = intent.getStringExtra("target")
        server!!.chatTwoPeople("test",target).enqueue(object : Callback<DMDTO>{
            override fun onFailure(call: Call<DMDTO>, t: Throwable) {
                Log.d("또안됐어?", t.toString())
            }

            override fun onResponse(call: Call<DMDTO>, response: Response<DMDTO>) {
                val DMList = ArrayList<DMset>()

                for(chat in response.body()!!.data){
                    Log.d("채팅내역", chat.toString())
                    var viewType:Int
                    if (chat.receive == "test") viewType = 0
                    else viewType = 1
                    DMList.add(DMset(chat.receive, chat.send, chat.message, chat.created, chat.readmessage, chat.picture,viewType))
                }

                val layoutManager = LinearLayoutManager(applicationContext)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                chatRecycler.layoutManager = layoutManager
                val adapter = DMAdapter(applicationContext, DMList)
                chatRecycler.adapter = adapter
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
        holder.itemView.messageAt.text = "${half} ${hour}:${DM.created[4]}"
        holder.itemView.messageAt.text = "${DM.created[0]}-${DM.created[1]}-${DM.created[2]} ${DM.created[3]}:${DM.created[4]}"
    }
}
