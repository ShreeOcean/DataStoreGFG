package com.ocean.datastoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.ocean.datastoreapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var age  = 0
    var name = ""
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager(this)
        buttonSave()
        observeData()
    }

    private fun observeData() {
        this.userManager.userAgeFlow.asLiveData().observe(this) {
            age = it
            binding.tvAge.text = it.toString()
        }
        userManager.userNameFlow.asLiveData().observe(this) {
            name = it
            binding.tvName.text = it.toString()
        }
    }

    private fun buttonSave() {
        binding.btnSave.setOnClickListener {

            name = binding.etName.text.toString()
            age = binding.etAge.text.toString().toInt()

            GlobalScope.launch { userManager.storeUser(age, name) }
        }
    }
}