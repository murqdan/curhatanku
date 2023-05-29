package com.murqdan.curhatanku.view.detailcurhatan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.murqdan.curhatanku.R
import com.murqdan.curhatanku.databinding.ActivityDetailCurhatanBinding
import com.murqdan.curhatanku.response.ListCurhatanItem

class DetailCurhatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCurhatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCurhatanBinding.inflate(layoutInflater)
        supportActionBar?.title = getString(R.string.title_activity_detail)
        setContentView(binding.root)

        @Suppress("DEPRECATION") val data = intent.getParcelableExtra<ListCurhatanItem>("DATA")
        val photo = data?.photoUrl
        val name = data?.name
        val description = data?.description

        Glide.with(this@DetailCurhatanActivity)
            .load(photo)
            .into(binding.ivDetailPhoto)
        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description
    }
}