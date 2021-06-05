package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.ListSectionHeaderBinding

class ChapterSectionViewHolder(
   itemView: View,
   onArrowSelected: (position: Int) -> Unit
) : SectionHeaderViewHolder(itemView, onArrowSelected)
{
   private val binding = ListSectionHeaderBinding.bind(itemView)

   init
   {
      binding.title.text = itemView.resources.getString(R.string.chapters)
   }
}
