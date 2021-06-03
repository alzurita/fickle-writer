package com.zurita.ficklewriter.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.R

class NoteActivity : AppCompatActivity()
{
   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.note_fragment)
   }
}
