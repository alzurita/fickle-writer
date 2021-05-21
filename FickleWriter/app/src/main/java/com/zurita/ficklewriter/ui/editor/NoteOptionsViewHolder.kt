package com.zurita.ficklewriter.ui.editor

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.NoteOptionsBinding

class NoteOptionsViewHolder(
   itemView: View,
   onPinSelected: (note: Note) -> Unit
) : RecyclerView.ViewHolder(itemView),
    NoteViewHolderIntf
{
   private val binding = NoteOptionsBinding.bind(itemView)

   private var note = Note()

   private val textPin = itemView.context.resources.getString(R.string.pin)
   private val textUnpin = itemView.context.resources.getString(R.string.unpin)

   init
   {
      binding.pin.setOnClickListener {
         note.pinned = !note.pinned
         setPinnedText()
         onPinSelected(note)
      }
   }

   override fun bind(item: Note)
   {
      note = item
      setPinnedText()
   }

   private fun setPinnedText()
   {
      binding.pin.text = if(note.pinned) textUnpin else textPin
   }
}
