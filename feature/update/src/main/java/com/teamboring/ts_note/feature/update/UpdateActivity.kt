package com.teamboring.ts_note.feature.update

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teamboring.ts_note.feature.update.databinding.ActivityUpdateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private val viewModel: UpdateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getIntExtra("noteId", -1).let {
            viewModel.loadData(it)
        }

        binding.backButton.setOnClickListener { finish() }

        binding.updateButton.setOnClickListener {
            viewModel.update(
                binding.titleView.text.toString(),
                binding.contentText.text.toString()
            )
        }

        loadNoteData()
        processIfStateChanged()
    }

    private fun processIfStateChanged() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    UpdateState.SUCCESS -> {
                        finish()
                    }

                    UpdateState.ERROR -> {
                        // Show error message
                    }

                    UpdateState.LOADING -> {
                        // Show loading
                    }

                    else -> {
                        // Hide loading
                    }
                }
            }
        }
    }

    private fun loadNoteData() {
        lifecycleScope.launch {
            viewModel.note.collect { note ->
                binding.titleView.setText(note.title)
                binding.contentText.setText(note.content)
            }
        }
    }
}