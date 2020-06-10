package com.example.dogplay

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.direct_message.*
import java.io.File
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.database.FirebaseDatabase as RealTimeDatabase
import com.google.firebase.storage.FirebaseStorage as ImageDatabase

class DirectMessage: AppCompatActivity() {
    private var realTimeDatabase = RealTimeDatabase.getInstance().reference
    private var imageDatabase = ImageDatabase.getInstance().reference
    private var storeDatabase = FirebaseFirestore.getInstance()
    private lateinit var formatter: SimpleDateFormat
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var currentPhotoPath: String
    private lateinit var userId: String
    private lateinit var userName: String
    private lateinit var userPicture: String
    private lateinit var targetId: String
    private lateinit var targetName: String
    private lateinit var targetPicture: String
    private lateinit var userChatId: String
    private lateinit var targetChatId: String
    private lateinit var chatRoomId: String
    private val dataSnapShots: ArrayList<DataSnapshot> = ArrayList()
    private val CAMERA_PERMISSION_REQUEST_CODE = 1006
    private val SAVE_IMAGE_REQUEST_CODE = 1007
    private var photoURI : Uri? = null
    private var picture: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.direct_message)

        userId = Supplier.UserId
        userName = Supplier.user.nickname
        userPicture = Supplier.user.picture!!
        targetId = intent.getStringExtra("targetId")!!
        targetName = intent.getStringExtra("targetName")!!
        targetPicture = intent.getStringExtra("targetPicture")!!

        hotelName.text = targetName
        Glide.with(this)
            .load(targetPicture)
            .into(hotelImg)

        formatter = SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
        mRecyclerView = chatRecycler
        mRecyclerView.adapter = RecyclerAdapter(this@DirectMessage, dataSnapShots)
        mRecyclerView.layoutManager = LinearLayoutManager(this@DirectMessage, RecyclerView.VERTICAL, false)

        storeDatabase.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }

        // 유저 채팅방 id
        storeDatabase.collection("users").whereEqualTo("userId", userId).get()
            .addOnSuccessListener {
                result ->
                if (result.documents.size != 0) {
                    userChatId = result.documents[0].id
                }
                else {
                    val document = storeDatabase.collection("users").document()
                    document.set(UserChatId(userId))
                    userChatId = document.id
                }
            }
        // 상대 채팅방 id
        storeDatabase.collection("users").whereEqualTo("userId", targetId).get()
            .addOnSuccessListener {
                    result ->
                if (result.documents.size != 0) {
                    targetChatId = result.documents[0].id
                }
                else {
                    val document = storeDatabase.collection("users").document()
                    document.set(UserChatId(targetId))
                    targetChatId = document.id
                }
            }

        // 채팅방 id
        storeDatabase.collection("chatRooms").whereEqualTo("user1", userId).whereEqualTo("user2", targetId).get()
            .addOnSuccessListener {
                    firstResult ->
                if (firstResult.documents.size != 0) {
                    chatRoomId = firstResult.documents[0].id
//                    Log.d("chatRoomId", chatRoomId)
                    getChatData(smoothScroller)
                }
                else {
                    storeDatabase.collection("chatRooms").whereEqualTo("user1", targetId).whereEqualTo("user2", userId).get()
                        .addOnSuccessListener {
                                secondResult ->
                            if (secondResult.documents.size != 0) {
                                chatRoomId = secondResult.documents[0].id
//                                Log.d("chatRoomId", chatRoomId)
                                getChatData(smoothScroller)
                            }
                            else {
                                val document = storeDatabase.collection("chatRooms").document()
                                document.set(ChatRoom(userId, targetId, userName, targetName, userPicture, targetPicture))
                                chatRoomId = document.id
                                getChatData(smoothScroller)
                            }
//                            Log.d("chatRoomId", chatRoomId)
                        }
                }
            }

        sendBtn.setOnClickListener {
            val message = sendMessage.text.toString()
            if (message != "") {
                send(message = message)
                sendMessage.setText("")
            }
        }

        takePhoto.setOnClickListener {
            prepTakePhoto()
        }
    }

    private fun getChatData(smoothScroller: RecyclerView.SmoothScroller) {
        realTimeDatabase.child("chats").child(chatRoomId).orderByChild("timestamp").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(dataSnapShot: DataSnapshot, previousChildName: String?) {
                dataSnapShots.add(dataSnapShot)
                (mRecyclerView.adapter as RecyclerAdapter).notifyDataSetChanged()

                // move scroll to bottom
                smoothScroller.targetPosition = dataSnapShots.size
                (mRecyclerView.layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)

                // last chat update
                val document = storeDatabase.collection("chatRooms").document(chatRoomId)
                document.update("lastChat", dataSnapShot.child("message").value)
                document.update("timestamp", dataSnapShot.child("timestamp").value)
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "메세지를 불러올 수 없습니다.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun send(message: String = "", picture: String = "") {
        var sendMessage = message
        if (picture != "") {
            sendMessage = "사진을 전송하였습니다."
        }
        val chat = Chat(userChatId, sendMessage, picture, System.currentTimeMillis())
        realTimeDatabase.child("chats").child(chatRoomId).push().setValue(chat)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun prepTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)  // arrayOf is to see array form
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    private fun takePhoto() {
        // also is like when in Kotlin
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                takePictureIntent -> takePictureIntent.resolveActivity(this.packageManager)
            if (takePictureIntent == null) {
                Toast.makeText(this, "사진을 저장할 수 없습니다.", Toast.LENGTH_LONG).show()
            } else {
                // if we are here, we have a valid intent
                val photoFile: File = createImageFile()
                photoFile.also {
                    photoURI = FileProvider.getUriForFile(this, "com.example.dogplay.fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, SAVE_IMAGE_REQUEST_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SAVE_IMAGE_REQUEST_CODE) {
                Toast.makeText(this, "이미지가 저장되었습니다.", Toast.LENGTH_LONG).show()
                val image = photoURI!!

                // firebase update
                val imageRef = imageDatabase.child("chat/${Supplier.UserId}/$targetId/" + image.lastPathSegment)
                val uploadTask = imageRef.putFile(image)
                uploadTask.addOnSuccessListener {
                    val downloadUrl = imageRef.downloadUrl
                    downloadUrl.addOnSuccessListener {
                        picture = it.toString()
                        send(picture = picture)
                    }
                }
            }
        }
    }

    private fun createImageFile(): File {
        // generate unique file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        // get access to the directory where we can write pictures.
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("DogPlay${timeStamp}", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {  // like a case switch
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    takePhoto()
                } else {
                    Toast.makeText(this, "카메라를 이용하시려면 이용 권한을 허락하셔야합니다.", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    inner class RecyclerAdapter(private val context: Context, private val messages: ArrayList<DataSnapshot>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            if (viewType == 0) {
                RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_send, parent, false))
            } else {
                RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_receieve, parent, false))
            }

        override fun getItemViewType(position: Int): Int =
            if (messages.elementAt(position).child("sender").value == userChatId) {
                0
            } else {
                1
            }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val message = messages.elementAt(position)
            holder.updateMessages(message)
        }

        override fun getItemCount(): Int = messages.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.chatImg)
        private val content: TextView = itemView.findViewById(R.id.msg)
        private val msgAt: TextView = itemView.findViewById(R.id.messageAt)

        fun updateMessages(message: DataSnapshot) {
            val picture = message.child("picture").value.toString()
            val msg = message.child("message").value.toString()
            val timestamp = message.child("timestamp").value

            if (picture != "") {
                Glide.with(itemView)
                    .load(picture)
                    .into(img)
                content.visibility = View.GONE
            }
            else {
                content.text = msg
                img.visibility = View.GONE
            }

            if (timestamp != null) {
                val formatted = formatter.format(timestamp)
                msgAt.text = formatted
            }
        }
    }

    data class UserChatId (
        var userId: String
    )

    data class Chat (
        var sender: String = "",
        var message: String = "",
        var picture: String = "",
        var timestamp: Long
    )

    data class ChatRoom (
        var user1: String = "",
        var user2: String = "",
        var userName1: String = "",
        var userName2: String = "",
        var userPicture1: String = "",
        var userPicture2: String = "",
        var lastChat: String = "",
        var timestamp: Long = 0
    )
}
