package com.teamboring.ts_note.feature.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.teamboring.ts_note.feature.write.databinding.ActivityWriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var viewModel: WriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WriteViewModel::class.java]

        setOnSaveButtonListener()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setOnSaveButtonListener() {
        binding.saveButton.setOnClickListener {
            val title = binding.titleView.text.toString()
            val content = binding.contentView.contentText.text.toString()

            viewModel.save(title, content)

            setResult(RESULT_OK)
            finish()
        }
    }
}