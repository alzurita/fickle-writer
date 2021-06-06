package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.ListOptionsBinding

interface NoteOptionsViewHolderListener
{
   fun onPinSelected(note: Note)
   fun onEditSelected(note: Note)
}

class NoteOptionsViewHolder(
   itemView: View,
   listener: NoteOptionsViewHolderListener
) : RecyclerView.ViewHolder(itemView),
    NoteViewHolderIntf
{
   private val binding = ListOptionsBinding.bind(itemView)

   private var note = Note()

   private val textPin = itemView.context.resources.getString(R.string.pin)

   init
   {
      binding.pin.setOnClickListener {
         listener.onPinSelected(note)
      }
      binding.edit.setOnClickListener {
         listener.onEditSelected(note)
      }
      binding.pin.text = textPin
   }

   override fun bind(item: Note)
   {
      note = item
   }
}
