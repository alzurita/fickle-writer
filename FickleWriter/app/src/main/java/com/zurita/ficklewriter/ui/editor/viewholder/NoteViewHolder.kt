package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.ListHeaderBinding

interface NoteViewHolderIntf
{
   fun bind(item: Note)
}

class NoteViewHolder(
   itemView: View,
   private val onClickListener: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView),
    NoteViewHolderIntf
{
   private val binding = ListHeaderBinding.bind(itemView)

   init
   {
      itemView.setOnClickListener { onClickListener(adapterPosition) }
   }

   override fun bind(item: Note)
   {
      binding.title.text = item.title
      binding.shortDescription.text = item.shortDescription
   }
}
