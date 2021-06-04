package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.SectionHeaderBinding

class SummarySectionViewHolder(
   itemView: View,
   onArrowSelected: (position: Int) -> Unit
) : SectionHeaderViewHolder(itemView, onArrowSelected)
{
   private val binding = SectionHeaderBinding.bind(itemView)

   init
   {
      binding.title.text = itemView.resources.getString(R.string.summary)
      binding.icon.setImageDrawable(ResourcesCompat.getDrawable(
         itemView.resources, R.drawable.ic_edit_pencil, itemView.context.theme)
      )
   }
}
