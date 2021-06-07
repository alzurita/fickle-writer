package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.ListHeaderBinding
import com.zurita.ficklewriter.ui.editor.EditorAdapterListener

class ChapterViewHolder(
   itemView: View,
   private val listener: EditorAdapterListener
) : RecyclerView.ViewHolder(itemView)
{
   val binding = ListHeaderBinding.bind(itemView)

   private var note: Note? = null

   init
   {
      binding.icon.setImageDrawable(
         itemView.resources.getDrawable(R.drawable.ic_pages, itemView.context.theme))

      binding.root.setOnClickListener { listener.onChapterSelected(note) }
   }

   fun bind(note: Note)
   {
      this.note = note
   }
}
