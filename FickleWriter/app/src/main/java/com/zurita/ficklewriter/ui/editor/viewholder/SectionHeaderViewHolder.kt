package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.ListSectionHeaderBinding

open class SectionHeaderViewHolder(
   itemView: View,
   onArrowSelected: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView)
{
   private val binding = ListSectionHeaderBinding.bind(itemView)
   init
   {
      binding.arrow.setOnClickListener { onArrowSelected(adapterPosition) }
   }
}
