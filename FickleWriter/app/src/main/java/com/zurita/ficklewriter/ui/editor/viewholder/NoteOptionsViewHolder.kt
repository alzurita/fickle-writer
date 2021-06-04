package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
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

   init
   {
      binding.pin.setOnClickListener {
         onPinSelected(note)
      }
      binding.pin.text = textPin
   }

   override fun bind(item: Note)
   {
      note = item
   }
}
