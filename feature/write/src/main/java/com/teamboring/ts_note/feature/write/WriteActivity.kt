package com.teamboring.ts_note.feature.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teamboring.ts_note.feature.write.databinding.ActivityWriteBinding

class WriteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}