package com.teamboring.ts_note.feature.update

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamboring.ts_note.feature.update.databinding.ActivityUpdateBinding

class UpdateActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}