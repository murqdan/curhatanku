package com.murqdan.curhatanku.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.murqdan.curhatanku.di.Injection
import com.murqdan.curhatanku.repository.CurhatankuRepository
import com.murqdan.curhatanku.view.addcurhatan.AddCurhatanViewModel
import com.murqdan.curhatanku.view.curhatan.CurhatanViewModel
import com.murqdan.curhatanku.view.login.LoginViewModel
import com.murqdan.curhatanku.view.register.RegisterViewModel

class ViewModelFactory(private val curhatankuRepository: CurhatankuRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurhatanViewModel::class.java) -> {
                CurhatanViewModel(curhatankuRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel() as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(curhatankuRepository) as T
            }
            modelClass.isAssignableFrom(AddCurhatanViewModel::class.java) -> {
                AddCurhatanViewModel(curhatankuRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown Model class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}