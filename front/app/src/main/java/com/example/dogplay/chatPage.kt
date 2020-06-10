package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogplay.API.Companion.server
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.chat_list.view.*
import kotlinx.android.synthetic.main.chat_page.*
import kotlinx.android.synthetic.main.chat_page.view.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 */
class chatPage : Fragment() {
    private var storeDatabase = FirebaseFirestore.getInstance()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var formatter: SimpleDateFormat
    private val userId = Supplier.UserId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chat_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        storeDatabase.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    override fun onResume() {
        super.onResume()
        mRecyclerView = chatrv
        formatter = SimpleDateFormat("yyyy:MM:dd HH:mm:ss")

        // 채팅방 가져오기
        storeDatabase.collection("chatRooms").get()
            .addOnSuccessListener {
                    result ->
                if (result.documents.size != 0) {

                    mRecyclerView.adapter = RecyclerAdapter(context!!, result.documents)
                    mRecyclerView.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
//                    for (document in result.documents) {
//                        Log.d("child", document.get())
//                    }
                }
//                else {
//                    val document = storeDatabase.collection("users").document()
//                    document.set(UserChatId(userId))
//                    userChatId = document.id
//                }
            }
    }

    inner class RecyclerAdapter(private val context: Context, private val chatRooms: MutableList<DocumentSnapshot>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false))


        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val chatRoom = chatRooms.elementAt(position)
            holder.updateChatRoom(chatRoom)
        }

        override fun getItemCount(): Int = chatRooms.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.chatpf)
        private val content: TextView = itemView.findViewById(R.id.chatTop)
        private val msgAt: TextView = itemView.findViewById(R.id.messageAt)
        private val targetName: TextView = itemView.findViewById(R.id.chatNic)

        fun updateChatRoom(chatRoom: DocumentSnapshot) {
            val user1 = chatRoom.get("user1").toString()
            val user2 = chatRoom.get("user2").toString()
            val userName1 = chatRoom.get("userName1").toString()
            val userName2 = chatRoom.get("userName2").toString()
            val userPicture1 = chatRoom.get("userPicture1").toString()
            val userPicture2 = chatRoom.get("userPicture2").toString()
            val msg = chatRoom.get("lastChat").toString()
            val timestamp = chatRoom.get("timestamp")

            var intentId = ""
            var intentName = ""
            var intentPicture = ""

            if (userId != user1) {
                // 채팅방 사진
                if (userPicture1 != "") {
                    Glide.with(itemView)
                        .load(userPicture1)
                        .into(img)
                }
                else {
                    img.setImageResource(R.drawable.dog)
                }
                // 상대 이름
                targetName.text = userName1
                // intent
                intentId = user1
                intentName = userName1
                intentPicture = userPicture1
            }
            else {
                // 채팅방 사진
                if (userPicture2 != "") {
                    Glide.with(itemView)
                        .load(userPicture2)
                        .into(img)
                }
                else {
                    img.setImageResource(R.drawable.dog)
                }
                // 상대 이름
                targetName.text = userName2
                // intent
                intentId = user2
                intentName = userName2
                intentPicture = userPicture2
            }
            content.text = msg

            if (timestamp != null && timestamp.toString() != "0") {
                val formatted = formatter.format(timestamp)
                msgAt.text = formatted
            }

            itemView.setOnClickListener{
                val intent = Intent(context, DirectMessage::class.java).apply {
                    putExtra("targetId", intentId)
                    putExtra("targetName", intentName)
                    putExtra("targetPicture", intentPicture)
                }
                context!!.startActivity(intent)
            }
        }
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
        if (chat.receive != Supplier.UserId) target = chat.receive
        else target = chat.send
        holder.itemView.chatNic.text = target.split('@')[0]
        holder.itemView.chatTop.text = chat.message
        holder.itemView.messageAt.text = "${chat.created[0]}-${chat.created[1]}-${chat.created[2]} ${chat.created[3]}:${chat.created[4]}"
        holder.itemView.setOnClickListener{
            val intent = Intent(context, DirectMessage::class.java)
            intent.putExtra("target",target)
            context.startActivity(intent)
        }
    }
}
