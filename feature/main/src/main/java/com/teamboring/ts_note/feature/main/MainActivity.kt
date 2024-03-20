package com.teamboring.ts_note.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamboring.ts_note.common.ResultState
import com.teamboring.ts_note.core.room.Note
import com.teamboring.ts_note.feature.main.databinding.ActivityMainBinding
import com.teamboring.ts_note.feature.write.WriteActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val writeActivityResultLauncher = getWriteActivityResultLauncher()
    private lateinit var noteAdapter: NoteAdapter
    private fun getWriteActivityResultLauncher() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.getAllNotes()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnAddButtonClickListener()
        settingAdapter()
        loadAllNotes()
    }

    private fun setOnAddButtonClickListener() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            writeActivityResultLauncher.launch(intent)
        }
    }

    private fun settingAdapter() {
        noteAdapter = NoteAdapter(onSelectItem = onSelectItem(), onDeleteItem = { a, b ->
            onDeleteNote(a, b) })
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = noteAdapter
    }

    private fun onDeleteNote(note: Note, adapterPosition: Int) {
        val dialog = AlertDialog.Builder(this, com.teamboring.ts_note.common.R.style.AlertDialogTheme)
            .setTitle("삭제")
            .setMessage("정말 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                viewModel.deleteNote(note)
                noteAdapter.removeItem(note, adapterPosition)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
        dialog.show()
    }

    private fun onSelectItem(): (Int) -> Unit = {
        val intent = Intent(this, WriteActivity::class.java)
        intent.putExtra("noteId", it)
        writeActivityResultLauncher.launch(intent)
    }

    private fun loadAllNotes() {
        lifecycleScope.launch {
            viewModel.notes.collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        binding.emptyTextView.visibility = View.GONE
                        noteAdapter.update(result.data)
                    }

                    is ResultState.Error -> {
                        binding.loadingProgressBar.visibility = View.GONE
                    }

                    is ResultState.Loading -> {
                        binding.loadingProgressBar.visibility = View.VISIBLE
                    }

                    is ResultState.Empty -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        binding.emptyTextView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

}