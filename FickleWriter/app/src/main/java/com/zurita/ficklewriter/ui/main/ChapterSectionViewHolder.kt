package com.zurita.ficklewriter.ui.main

import android.view.View
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.SectionHeaderBinding

class ChapterSectionViewHolder(
   itemView: View
) : SectionHeaderViewHolder(itemView)
{
   private val binding = SectionHeaderBinding.bind(itemView)

   init
   {
      binding.title.text = itemView.resources.getString(R.string.chapters)
   }
}