package com.murqdan.curhatanku.view.curhatan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.murqdan.curhatanku.view.ViewModelFactory
import com.murqdan.curhatanku.view.login.LoginActivity
import com.murqdan.curhatanku.R
import com.murqdan.curhatanku.databinding.ActivityCurhatanBinding
import com.murqdan.curhatanku.adapter.CurhatanListAdapter
import com.murqdan.curhatanku.paging.CurhatanPagingViewModel
import com.murqdan.curhatanku.adapter.LoadingStateAdapter
import com.murqdan.curhatanku.paging.ViewModelFactoryPaging
import com.murqdan.curhatanku.response.ListCurhatanItem
import com.murqdan.curhatanku.view.addcurhatan.AddCurhatanActivity
import com.murqdan.curhatanku.view.maps.MapsActivity

class CurhatanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurhatanBinding
    private lateinit var factory: ViewModelFactory
    private val curhatanViewModel: CurhatanViewModel by viewModels { factory }
    private lateinit var curhatan: ArrayList<ListCurhatanItem>
    private val curhatanPagingViewModel: CurhatanPagingViewModel by viewModels {
        ViewModelFactoryPaging(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurhatanBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.curhatanList.layoutManager = layoutManager

        settingAdapter()
        setupAction()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.look_maps -> {
                startActivity(Intent(this, MapsActivity::class.java).putParcelableArrayListExtra("location",curhatan))
                return true
            }
            R.id.add_curhatan -> {
                startActivity(Intent(this, AddCurhatanActivity::class.java))
                return true
            }
            R.id.action_logout-> {
                curhatanViewModel.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
            else -> true
        }
    }

    private fun settingAdapter() {
        val adapter = CurhatanListAdapter()
        binding.curhatanList.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        curhatanPagingViewModel.curhatanPaging.observe(this) {
            adapter.submitData(lifecycle, it)
        }
        curhatanViewModel.listCurhatan.observe(this) {
            curhatan = it.listCurhatan as ArrayList<ListCurhatanItem>
        }
    }

    private fun setupAction() {
        curhatanViewModel.getUser().observe(this@CurhatanActivity) {
            val token = it.token
            if(it.isLogin) {
                supportActionBar?.title = "Hai, ${it.name}!"
                curhatanViewModel.getCurhatanData(token)
            } else {
                startActivity(Intent(this@CurhatanActivity, LoginActivity::class.java))
            }
        }
    }
}