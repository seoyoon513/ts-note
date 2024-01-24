package com.teamboring.ts_note.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamboring.ts_note.common.ResultState
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
        noteAdapter = NoteAdapter()
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = noteAdapter
    }

    private fun loadAllNotes() {
        lifecycleScope.launch {
            viewModel.notes.collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        noteAdapter.update(result.data)
                    }

                    is ResultState.Error -> {

                    }

                    is ResultState.Loading -> {

                    }

                    is ResultState.Empty -> {

                    }
                }
            }
        }
    }
}