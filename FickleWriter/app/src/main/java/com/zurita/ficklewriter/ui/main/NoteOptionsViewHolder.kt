package com.zurita.ficklewriter.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.NoteOptionsBinding

class NoteOptionsViewHolder(
   itemView: View,
   onPinSelected: (note: Note) -> Unit
) : RecyclerView.ViewHolder(itemView)
{
   val binding = NoteOptionsBinding.bind(itemView)

   var note = Note()

   init
   {
      binding.pin.setOnClickListener { onPinSelected(note) }
   }
}
