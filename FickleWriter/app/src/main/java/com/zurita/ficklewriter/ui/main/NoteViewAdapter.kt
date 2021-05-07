package com.zurita.ficklewriter.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R

class NoteViewAdapter(
   private val inflater : LayoutInflater
) : RecyclerView.Adapter<NoteViewHolder>() {
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
      val view = inflater.inflate(R.layout.note, parent, false)
      return NoteViewHolder(view)
   }

   override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

   }

   override fun getItemCount(): Int {
      return 5;
   }
}