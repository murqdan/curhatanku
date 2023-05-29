package com.murqdan.curhatanku.view.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.murqdan.curhatanku.view.ViewModelFactory
import com.murqdan.curhatanku.view.login.LoginActivity
import com.murqdan.curhatanku.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupAction()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = "masukkan nama anda"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "masukkan email anda"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "masukkan password anda"
                }
                else -> {
                    registerViewModel.postRegisterData(
                        binding.edRegisterName.text.toString(),
                        binding.edRegisterEmail.text.toString(),
                        binding.edRegisterPassword.text.toString()
                    )
                    registerViewModel.register.observe(this@RegisterActivity) {
                        response -> if (!response.error) {
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        }
                    }
                }
            }
        }

        binding.toLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}