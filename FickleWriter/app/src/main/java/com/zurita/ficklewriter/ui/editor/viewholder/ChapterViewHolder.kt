package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.ListHeaderBinding

class ChapterViewHolder(
   itemView: View
) : RecyclerView.ViewHolder(itemView)
{
   val binding = ListHeaderBinding.bind(itemView)

   init
   {
      binding.icon.setImageDrawable(
         itemView.resources.getDrawable(R.drawable.ic_pages, itemView.context.theme))
   }
}
