package com.zurita.ficklewriter.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.NotesContainerActivityBinding
import com.zurita.ficklewriter.ui.main.CustomIntent.DATA_NOTE

class NotesContainerActivity : AppCompatActivity()
{
   var _binding: NotesContainerActivityBinding? = null
   val binding: NotesContainerActivityBinding
      get()
      {
         return _binding!!
      }

   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.notes_container_activity)
      _binding = NotesContainerActivityBinding.bind(findViewById(R.id.container))

      if (isInMultiWindowMode)
      {
         // Remove the toolbar if we are in windowed mode, because
         // there's not enough room for it
         binding.root.removeView(binding.toolbar.rootView)
      }
      else
      {
         setSupportActionBar(binding.toolbar)
      }

      val note = intent.getParcelableExtra<Note>("note")
   }

   override fun onNewIntent(intent: Intent?)
   {
      super.onNewIntent(intent)

      val note = intent?.getParcelableExtra<Note>(DATA_NOTE)
   }
}
