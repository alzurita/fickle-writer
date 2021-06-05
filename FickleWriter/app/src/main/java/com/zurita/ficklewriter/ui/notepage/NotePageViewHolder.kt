package com.zurita.ficklewriter.ui.notepage

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.NoteViewBinding

class NotePageViewHolder(
   view: View
) : RecyclerView.ViewHolder(view)
{
   private val binding = NoteViewBinding.bind(view)

   fun bind(note: Note)
   {
      binding.editText.text?.clear()
      binding.editText.text?.append(note.title)
   }
}