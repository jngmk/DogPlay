package com.example.dogplay

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import kotlinx.android.synthetic.main.fragment_add_my_dog_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMyDogInfo : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSpinner: Spinner
    private lateinit var mAutoCompleteTextView: AutoCompleteTextView
    private lateinit var dogs: ArrayList<DogInfo>
    private lateinit var species: ArrayList<Species>
    private var storageReferenence = FirebaseStorage.getInstance().getReference()
    private lateinit var userId: String
    private var dogImage: Uri? = null
    private var dogToPost = DogInfoToPost()
    private var dog = DogInfo()
    private var precautions = ArrayList<ArrayList<String>>()
    private val IMAGE_GALLERY_REQUEST_CODE = 1004

    companion object {
        fun newInstance() = AddMyDogInfo()
    }

    init {
        val precaution1 = ArrayList<String>()
        val precaution2 = ArrayList<String>()

        precaution1.add("성격")
        precaution1.add("지치지 않고 노는 것을 좋아합니다.")
        precaution2.add("습관")
        precaution2.add("높은 곳에 자주 앉아있습니다.")

        precautions.add(precaution1)
        precautions.add(precaution2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_my_dog_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecyclerView = rcyPrecaution
        mSpinner = spnSpecies
//        mAutoCompleteTextView = spnSpecies
        dogs = Supplier.dogs
        userId = Supplier.user.userid

        btnBack.setOnClickListener {
            (activity as AddMyDog).onOpenAddDog()
        }
        btnSave.setOnClickListener {
            if (edtEnterName.text == null || edtEnterName.text.toString() == "") {
                Toast.makeText(this.context, "이름을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnterAge.text == null || edtEnterAge.text.toString() == "") {
                Toast.makeText(this.context, "생년월일을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnterSize.text == null && edtEnterSize.text.toString() == "") {
                Toast.makeText(this.context, "몸무게를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (!rbMale.isChecked && !rbFemale.isChecked && !rbAsexual.isChecked) {
                Toast.makeText(this.context, "성별을 선택해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (dogToPost.speciesId == 0) {
                Toast.makeText(this.context, "견종을 선택해주세요.", Toast.LENGTH_LONG).show()
            }
            else {
                save()
            }
        }
        btnAddPrecaution.setOnClickListener {
            addPrecaution()
        }
        imgProfile.setOnClickListener {
            selectImage()
        }

        getSpecies()

        mRecyclerView.adapter = RecyclerAdapter(precautions)
        mRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun save() {
        // prepare
        prepare()

        // firebase update
        if (dogImage != null) {
            val imageRef = storageReferenence.child("users/${userId}/pet/${dogToPost.dogname}")
            val uploadTask = imageRef.putFile(dogImage!!)
            uploadTask.addOnSuccessListener {
                val downloadUrl = imageRef.downloadUrl
                downloadUrl.addOnSuccessListener {
                    dog.picture = it.toString()
                    dogToPost.picture = it.toString()
                    saveDogInfo()
                }
            }
            uploadTask.addOnFailureListener {
                Log.e(ContentValues.TAG, it.message!!)
            }
        }
        else {
            saveDogInfo()
        }
    }

    private fun saveDogInfo() {
        val server = API.server()
        server!!.postDogInfo(dogToPost).enqueue(object :
            Callback<RoomCountReturnData> {
            override fun onFailure(call: Call<RoomCountReturnData>, t: Throwable) {
//                Toast.makeText(context, "반려견 등록에 실패했습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RoomCountReturnData>, response: Response<RoomCountReturnData>) {
//                Toast.makeText(context, "반려견이 등록되었습니다.", Toast.LENGTH_LONG).show()
                val dogId = response.body()!!.data
                dog.id = dogId
                dogs.add(dog)
                MutableSupplier.dogs.postValue(dogs)
                clearAll()
                (activity as AddMyDog).onOpenAddDog()
            }
        })
    }

    private fun clearAll() {
        edtEnterName.setText("")
        edtEnterSize.setText("")
        edtEnterAge.setText("")
        rbGroupGender.clearCheck()
    }

    private fun prepare() {
        dog.userid = userId
        dogToPost.userid = userId

        dog.dogname = edtEnterName.text.toString()
        dogToPost.dogname = edtEnterName.text.toString()

        dog.size = edtEnterSize.text.toString().toInt()
        dogToPost.size = edtEnterSize.text.toString().toInt()

        val idx = rbGroupGender.checkedRadioButtonId
        val selectedRadioButton = rbGroupGender.findViewById<RadioButton>(idx)
        dog.gender = selectedRadioButton.tag.toString().toInt()
        dogToPost.gender = selectedRadioButton.tag.toString().toInt()

        val age = edtEnterAge.text.toString()
        val ageList = ArrayList<Int>()
        val ageStringList = ArrayList<String>()
        age.split('-').forEach {
            ageList.add(it.toInt())
            if (it.length < 4) {
                ageStringList.add(it.padStart(2, '0'))
            }
            else {
                ageStringList.add(it)
            }
        }
        dog.age = ageList
        dogToPost.age = ageStringList.joinToString("-")

        val count = precautions.size - 1
        for (i in 0..count) {
            val itemView = rcyPrecaution.findViewHolderForLayoutPosition(i)!!.itemView
            val title: String = itemView.findViewById<EditText>(R.id.title).text.toString()
            val content: String = itemView.findViewById<EditText>(R.id.content).text.toString()
            val array = ArrayList<String>()
            array.add(title)
            array.add(content)
            dog.detail.add(array)
            dogToPost.detail.addProperty(title, content)
        }
    }

    private fun addPrecaution() {
        val precaution = ArrayList<String>()
        precaution.add("특징")
        precaution.add("호텔에서 알아두면 좋은 점을 적어주세요.")
        precautions.add(precaution)

        (mRecyclerView.adapter as RecyclerAdapter).notifyDataSetChanged()
    }

    private fun selectImage() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
        }
    }

    private fun getSpecies() {
        val server = API.server()
        server!!.getSpecies().enqueue(object :
            Callback<SpeciesDTO> {
            override fun onFailure(call: Call<SpeciesDTO>, t: Throwable) {
//                Toast.makeText(context, "반려견 등록에 실패했습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<SpeciesDTO>, response: Response<SpeciesDTO>) {
//                Toast.makeText(context, "반려견이 등록되었습니다.", Toast.LENGTH_LONG).show()
                species = response.body()!!.data

//                mAutoCompleteTextView.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, species))
//                mAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
//                    val dogSpecies = parent.getItemAtPosition(position) as Species
//                    dog.speciesId = dogSpecies.id
//                    dogToPost.speciesId = dogSpecies.id
//                }

                mSpinner.adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, species)
                mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val dogSpecies = parent?.getItemAtPosition(position) as Species
                        dog.speciesId = dogSpecies.id
                        dogToPost.speciesId = dogSpecies.id
                    }
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_CODE) {
                if (data != null && data.data != null) {  // data: stuff back to us, data.data: image uri user select
                    val image = data.data
                    val source = ImageDecoder.createSource(activity!!.contentResolver, image!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)

                    imgProfile.setImageBitmap(bitmap)
                    dogImage = image
                }
            }
        }
    }

    inner class RecyclerAdapter(private val precautions: ArrayList<ArrayList<String>>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.add_my_dog_info_item, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val precaution = precautions[position]
            holder.updatePrecautions(precaution, position)

            val btnDelete: ImageView = holder.itemView.findViewById(R.id.btnDelete)
            btnDelete.setOnClickListener {
                precautions.removeAt(position)

                (mRecyclerView.adapter as RecyclerAdapter).notifyDataSetChanged()
            }
        }

        override fun getItemCount(): Int = precautions.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: EditText = itemView.findViewById(R.id.title)
        private var content: EditText = itemView.findViewById(R.id.content)

        fun updatePrecautions(precaution: ArrayList<String>, position: Int) {
            title.setText(precaution[0])
            content.hint = precaution[1]
        }
    }
}
