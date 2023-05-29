package com.murqdan.curhatanku.view.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.murqdan.curhatanku.model.UserModel
import com.murqdan.curhatanku.view.ViewModelFactory
import com.murqdan.curhatanku.view.curhatan.CurhatanActivity
import com.murqdan.curhatanku.view.register.RegisterActivity
import com.murqdan.curhatanku.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupAction()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "masukkan email anda"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "masukkan password anda"
                }
                else -> {
                    loginViewModel.postLoginData(
                        binding.edLoginEmail.text.toString(),
                        binding.edLoginPassword.text.toString()
                    )
                    loginViewModel.login.observe(this@LoginActivity) {
                        response -> loginViewModel.postUser(
                            UserModel(
                                response.loginResult.name,
                                "Bearer " + (response.loginResult.token),
                                true
                            )
                        )
                    }
                    loginViewModel.userLogin()
                    loginViewModel.login.observe(this@LoginActivity) {
                        response -> if (!response.error) {
                            startActivity(Intent(this@LoginActivity, CurhatanActivity::class.java))
                        }
                    }
                }
            }
        }

        binding.toRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}