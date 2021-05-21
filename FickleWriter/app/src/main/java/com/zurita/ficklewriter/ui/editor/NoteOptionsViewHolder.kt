package com.zurita.ficklewriter.ui.editor

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.NoteOptionsBinding

class NoteOptionsViewHolder(
   itemView: View,
   onPinSelected: (note: Note) -> Unit
) : RecyclerView.ViewHolder(itemView),
    NoteViewHolderIntf
{
   private val binding = NoteOptionsBinding.bind(itemView)

   private var note = Note()

   init
   {
      binding.pin.setOnClickListener {
         note.pinned = !note.pinned
         binding.pin.text = if(note.pinned) "Unpin" else "Pin"
         onPinSelected(note)
      }
   }

   override fun bind(item: Note)
   {
      note = item
      binding.pin.text = if(note.pinned) "Unpin" else "Pin"
   }
}
