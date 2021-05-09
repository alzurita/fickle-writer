package com.zurita.ficklewriter.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.NoteBinding

class NoteViewHolder(
   itemView: View,
   private val onClickListener: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView)
{
   init
   {
      itemView.setOnClickListener { onClickListener(adapterPosition) }
   }
}