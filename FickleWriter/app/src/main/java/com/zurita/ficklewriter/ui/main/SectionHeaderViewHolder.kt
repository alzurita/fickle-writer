package com.zurita.ficklewriter.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.databinding.SectionHeaderBinding

open class SectionHeaderViewHolder(
   itemView: View,
   onArrowSelected: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView)
{
   private val binding = SectionHeaderBinding.bind(itemView)
   init
   {
      binding.arrow.setOnClickListener { onArrowSelected(adapterPosition) }
   }
}
