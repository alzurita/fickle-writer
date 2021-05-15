package com.zurita.ficklewriter.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.NoteOptionsBinding

class NoteOptionsViewHolder(
   itemView: View,
   onPinSelected: (note: Note, position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView),
    NoteViewHolderIntf
{
   private val binding = NoteOptionsBinding.bind(itemView)

   private var note = Note()

   init
   {
      binding.pin.setOnClickListener { onPinSelected(note, adapterPosition) }
   }

   override fun bind(item: Note)
   {
      note = item
   }
}
