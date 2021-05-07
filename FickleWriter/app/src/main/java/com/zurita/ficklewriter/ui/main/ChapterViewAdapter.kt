package com.zurita.ficklewriter.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R

class ChapterViewAdapter(
   private val inflater : LayoutInflater
) : RecyclerView.Adapter<ChapterViewHolder>() {
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
      val view = inflater.inflate(R.layout.chapter_overview, parent, false)
      return ChapterViewHolder(view)
   }

   override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {

   }

   override fun getItemCount(): Int {
      return 5
   }
}