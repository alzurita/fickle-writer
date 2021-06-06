package com.zurita.ficklewriter.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.model.NotesContainerViewModel
import com.zurita.ficklewriter.ui.main.CustomIntent.DATA_NOTE

class NotesContainerActivity : AppCompatActivity()
{
   private val viewModel: NotesContainerViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.notes_container_activity)

      val note = intent?.getParcelableExtra<Note>(DATA_NOTE)
      viewModel.addNote(note)
      viewModel.isInWindowedMode = isInMultiWindowMode

      supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NotesContainerFragment.newInstance())
            .commitNow()
   }

   override fun onNewIntent(intent: Intent?)
   {
      super.onNewIntent(intent)

      val note = intent?.getParcelableExtra<Note>(DATA_NOTE)
      viewModel.addNote(note)
   }
}
