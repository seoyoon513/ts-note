package com.teamboring.ts_note.feature.write

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teamboring.ts_note.feature.write.databinding.ActivityWriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    private val viewModel: WriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnHasNoteData()
        setOnBackButtonListener()
        setOnSaveResultListener()
    }

    private fun setOnSaveResultListener() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                val isLoading = state == SaveState.LOADING
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                if (state == SaveState.SUCCESS) {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun setOnHasNoteData() {
        val noteId = intent.getIntExtra("noteId", -1)
        val hasData = noteId != -1
        loadIfHasData(hasData, noteId)
        settingIfHasNotData(hasData)
    }

    private fun settingIfHasNotData(hasData: Boolean) {
        if (!hasData) {
            binding.saveButton.text = resources.getString(R.string.save_button)
            setOnSaveButtonListener()
        }
    }

    private fun loadIfHasData(hasData: Boolean, noteId: Int) {
        if (hasData) {
            viewModel.loadData(noteId)
            watchNoteData()
            binding.saveButton.text = resources.getString(R.string.update_button)
            setOnUpdateButtonListener()
        }
    }

    private fun watchNoteData() {
        lifecycleScope.launch {
            viewModel.note.collect { note ->
                binding.titleView.setText(note.title)
                binding.contentText.setText(note.content)
                binding.dateView.text = note.date
            }
        }
    }

    private fun setOnUpdateButtonListener() {
        binding.saveButton.setOnClickListener {
            viewModel.update(
                binding.titleView.text.toString(),
                binding.contentText.text.toString()
            )
        }
    }

    private fun setOnBackButtonListener() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setOnSaveButtonListener() {
        binding.saveButton.setOnClickListener {
            viewModel.save(
                binding.titleView.text.toString(),
                binding.contentText.text.toString()
            )
        }
    }
}