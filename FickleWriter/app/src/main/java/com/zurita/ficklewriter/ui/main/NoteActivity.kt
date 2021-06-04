package com.zurita.ficklewriter.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.NoteFragmentBinding
import com.zurita.ficklewriter.databinding.NoteFragmentWindowedBinding
import com.zurita.ficklewriter.ui.editor.Note
import com.zurita.ficklewriter.ui.main.CustomIntent.DATA_NOTE

class NoteActivity : AppCompatActivity()
{
   private var binding: NoteFragmentBinding? = null

   private var bindingWindowed: NoteFragmentWindowedBinding? = null

   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)

      // I am using two different layouts because I can't find a way
      // to 'overload' the resource dir for windowed mode
      if(isInMultiWindowMode)
      {
         setContentView(R.layout.note_fragment_windowed)
         bindingWindowed = NoteFragmentWindowedBinding.bind(findViewById(R.id.container))
      }
      else
      {
         setContentView(R.layout.note_fragment)
         binding = NoteFragmentBinding.bind(findViewById(R.id.container))
      }

      val note = intent.getParcelableExtra<Note>("note")

      addTab(note)
   }

   override fun onNewIntent(intent: Intent?)
   {
      super.onNewIntent(intent)

      val note = intent?.getParcelableExtra<Note>(DATA_NOTE)

      addTab(note)
   }

   private fun addTab(note: Note?)
   {

   }
}
