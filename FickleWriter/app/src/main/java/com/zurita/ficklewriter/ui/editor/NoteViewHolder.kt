package com.zurita.ficklewriter.ui.editor

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.NoteBinding

class NoteViewHolder(
   itemView: View,
   private val onClickListener: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView),
    NoteViewHolderIntf
{
   private val binding = NoteBinding.bind(itemView)

   init
   {
      itemView.setOnClickListener { onClickListener(adapterPosition) }
   }

   override fun bind(item: Note)
   {
      binding.noteHeader.title.text = item.title
      binding.noteHeader.shortDescription.text = item.shortDescription
   }
}
