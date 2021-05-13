package com.zurita.ficklewriter.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.NoteBinding
import com.zurita.ficklewriter.databinding.NoteHeaderBinding

class ChapterViewHolder(
   itemView: View
) : RecyclerView.ViewHolder(itemView)
{
   val binding = NoteBinding.bind(itemView)

   init
   {
      binding.noteHeader.icon.setImageDrawable(
         itemView.resources.getDrawable(R.drawable.ic_pages, itemView.context.theme))
   }
}
