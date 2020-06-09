package com.example.dogplay

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class DirectMessage: AppCompatActivity() {
    private lateinit var currentPhotoPath: String
    private lateinit var target: String
    private val CAMERA_PERMISSION_REQUEST_CODE = 1006
    private val SAVE_IMAGE_REQUEST_CODE = 1007
    private var photoURI : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.direct_message)
        val server = server()
        target = intent.getStringExtra("target")!!
        hotelName.text = target.split('@')[0]
        Log.d("타겟", target)

        sendBtn.setOnClickListener {
            if (sendMessage.text.isEmpty()) {
                Log.d("비었다", "하하")
            } else {
                val picture = ""
                postMessage(picture)
            }
        }

        takePhoto.setOnClickListener {
            prepTakePhoto()
            if (sendMessage.text.isEmpty()) {
                Log.d("비었다", "하하")
            } else {
                val picture = ""
                postMessage(picture)
            }
        }

        server!!.chatTwoPeople(Supplier.UserId, target).enqueue(object : Callback<DMDTO> {
            override fun onFailure(call: Call<DMDTO>, t: Throwable) {
                Log.d("또안됐어?", t.toString())
            }

            override fun onResponse(call: Call<DMDTO>, response: Response<DMDTO>) {
                var DMList = ArrayList<DMset>()
                Log.d("채팅내역", response.body().toString())
                for (chat in response.body()!!.data) {
                    var viewType: Int
                    if (chat.receive == Supplier.UserId) viewType = 1
                    else viewType = 0
                    DMList.add(
                        DMset(
                            chat.receive,
                            chat.send,
                            chat.message,
                            chat.created,
                            chat.readmessage,
                            chat.picture,
                            viewType
                        )
                    )
                    server.ChatUpdate(
                        ChatIn(
                            chat.id,
                            chat.chatid,
                            chat.receive,
                            chat.send,
                            chat.picture,
                            chat.message,
                            chat.created,
                            1
                        )
                    ).enqueue(object : Callback<Any> {
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
                chatRecycler.scrollToPosition(adapter.itemCount - 1)
            }
        })
    }

    private fun postMessage(picture: String) {
        val server = server()
        Log.d("뭐라 친건데", sendMessage.text.toString())
        server!!.chatTwoPeople(Supplier.UserId, target).enqueue(object : Callback<DMDTO> {
            override fun onFailure(call: Call<DMDTO>, t: Throwable) {
            }

            override fun onResponse(call: Call<DMDTO>, response: Response<DMDTO>) {
                Log.d("DM", response.body().toString())
                val chatid = response.body()!!.data[0].chatid
                var receiver: String
                if (response.body()!!.data[0].receive == Supplier.UserId) {
                    receiver = response.body()!!.data[0].send
                } else {
                    receiver = response.body()!!.data[0].receive
                }

                server!!.PostChatInsert(
                    ChatInsert(
                        chatid,
                        "",
                        0,
                        sendMessage.text.toString(),
                        picture,
                        0,
                        receiver,
                        Supplier.UserId
                    )
                ).enqueue(object : Callback<DMSend> {
                    override fun onFailure(call: Call<DMSend>, t: Throwable) {
                        Log.d("말썽이네", t.toString())
                    }

                    override fun onResponse(call: Call<DMSend>, response: Response<DMSend>) {
                        Log.d("될줄 알았어", response.body().toString())
                        val layoutInflater = getLayoutInflater()
                        val chatview = layoutInflater.inflate(R.layout.chat_send, null)
                        Supplier.DMList.add(
                            DMset(
                                Supplier.UserId, receiver, sendMessage.text.toString(),
                                response.body()!!.data, 0, picture, 0
                            )
                        )
                        sendMessage.setText("")
                        chatRecycler.adapter!!.notifyDataSetChanged()
                        chatRecycler.scrollToPosition(chatRecycler.adapter!!.itemCount - 1)
                    }
                })
            }
        })
    }

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
                Toast.makeText(this, "Unable to save photo", Toast.LENGTH_LONG).show()
            } else {
                // if we are here, we have a valid intent
                val photoFile: File = createImageFile()
                photoFile.also {
                    photoURI = FileProvider.getUriForFile(this, "com.myplantdiary.android.FileProvider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, SAVE_IMAGE_REQUEST_CODE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        // generate unique file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        // get access to the directory where we can write pictures.
        val storageDir: File? = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("PlantDiary${timeStamp}", ".jpg", storageDir).apply {
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

    inner class DMAdapter(var context: Context, var DMs: ArrayList<DMset>) :
        RecyclerView.Adapter<DMAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            init {
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            Log.d("viewType", parent.context.toString())
            val context = parent.context
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            var view: View
            if (viewType == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.chat_send, parent, false)
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.chat_receieve, parent, false)
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
            val target: String
            var half: String
            var hour: Int
            holder.itemView.msg.text = DM.message
            if (DM.created[3] > 12) {
                half = "오후"; hour = DM.created[3] - 12
            } else if (DM.created[3] == 12) {
                half = "오후"; hour = 12
            } else {
                half = "오전"; hour = DM.created[3]
            }
            var min: String
            if (DM.created[4] < 10) {
                min = "0${DM.created[4]}"
            } else {
                min = "${DM.created[4]}"
            }
            holder.itemView.messageAt.text = "${half} ${hour}:${DM.created[4]}"
            holder.itemView.messageAt.text =
                "${DM.created[0]}-${DM.created[1]}-${DM.created[2]} ${DM.created[3]}:${min}"
        }
    }
}
