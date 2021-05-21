package com.zurita.ficklewriter.ui.editor

import android.view.View
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.SectionHeaderBinding

class ChapterSectionViewHolder(
   itemView: View,
   onArrowSelected: (position: Int) -> Unit
) : SectionHeaderViewHolder(itemView, onArrowSelected)
{
   private val binding = SectionHeaderBinding.bind(itemView)

   init
   {
      binding.title.text = itemView.resources.getString(R.string.chapters)
   }
}