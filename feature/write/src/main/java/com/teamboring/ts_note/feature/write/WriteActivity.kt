package com.teamboring.ts_note.feature.write

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.teamboring.ts_note.core.room.Note
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

    private fun setOnSaveResultListener() = lifecycleScope.launch {
        viewModel.state.collect { state ->
            showLoadingProgressBar(state.loadState)

            when (state.loadState) {
                NoteLoadState.ADDED, NoteLoadState.UPDATED -> {
                    setResult(RESULT_OK)
                    finish()
                }

                NoteLoadState.ERROR -> {
                    Toast.makeText(this@WriteActivity, "저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    binding.titleView.text.clear()
                    binding.contentText.text.clear()
                }
            }
        }
    }

    private fun showLoadingProgressBar(state: NoteLoadState) {
        binding.progressBar.visibility =
            if (state == NoteLoadState.LOADING) View.VISIBLE else View.GONE
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
            setNoteDataIfLoaded()
            binding.saveButton.text = resources.getString(R.string.update_button)
            setOnUpdateButtonListener()
        }
    }

    private fun setNoteDataIfLoaded() = lifecycleScope.launch {
        viewModel.state.collect { noteState ->
            if (noteState.loadState == NoteLoadState.ADDED) {
                setNoteData(noteState.note)
            }
        }
    }

    private fun setNoteData(note: Note) {
        binding.titleView.setText(note.title)
        binding.contentText.setText(note.content)
        binding.dateView.text = note.date
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