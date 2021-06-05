package com.zurita.ficklewriter.ui.notepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note

class NotePageAdapter(
   private val inflater: LayoutInflater,
   private val notes: List<Note>
) : RecyclerView.Adapter<NotePageViewHolder>()
{
   override fun getItemCount(): Int
   {
      return notes.size
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): NotePageViewHolder
   {
      val view = inflater.inflate(R.layout.note_view, parent, false)
      return NotePageViewHolder(view)
   }

   override fun onBindViewHolder(
      holder: NotePageViewHolder,
      position: Int
   )
   {
      holder.bind(notes[position])
   }
}