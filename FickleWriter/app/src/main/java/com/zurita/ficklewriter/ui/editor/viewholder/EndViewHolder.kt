package com.zurita.ficklewriter.ui.editor.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R

class EndViewHolder(
   itemView: View
) : RecyclerView.ViewHolder(itemView)
{
   init
   {
      itemView.minimumHeight = itemView.resources.getDimension(R.dimen.endHeight).toInt()
   }
}
