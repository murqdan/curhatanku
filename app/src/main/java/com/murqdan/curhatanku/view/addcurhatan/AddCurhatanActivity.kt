package com.murqdan.curhatanku.view.addcurhatan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.murqdan.curhatanku.R
import com.murqdan.curhatanku.databinding.ActivityAddCurhatanBinding
import com.murqdan.curhatanku.utils.reduceFileImage
import com.murqdan.curhatanku.utils.uriToFile
import com.murqdan.curhatanku.view.ViewModelFactory
import com.murqdan.curhatanku.view.curhatan.CurhatanActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddCurhatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCurhatanBinding
    private lateinit var factory: ViewModelFactory
    private val addCurhatanViewModel: AddCurhatanViewModel by viewModels { factory }
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCurhatanBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        supportActionBar?.title = getString(R.string.title_activity_add)
        setContentView(binding.root)

        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonAdd.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        addCurhatanViewModel.getUser().observe(this@AddCurhatanActivity) {
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                uploadResponse(
                    it.token,
                    imageMultipart,
                    binding.edAddDescription.text.toString().toRequestBody("text/plain".toMediaType())
                )
            } else {
                Toast.makeText(applicationContext, "Pilih gambar dahulu dari galeri", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddCurhatanActivity)
                getFile = myFile
                binding.ivGallery.setImageURI(uri)
            }
        }
    }

    private fun uploadResponse(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ) {
        addCurhatanViewModel.postCurhatanData(token, file, description)
        addCurhatanViewModel.addCurhatan.observe(this@AddCurhatanActivity) {
            if (!it.error) {
                val intent = Intent(this@AddCurhatanActivity, CurhatanActivity::class.java)
                startActivity(intent)
            }
        }
    }
}