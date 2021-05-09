package com.zurita.ficklewriter.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R

class NoteViewAdapter(
   private val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   private val itemTypeList = mutableListOf(
      NoteHeader, NoteHeader, NoteHeader
   )

   private var previousIndexSelected = -1

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): RecyclerView.ViewHolder
   {
      return when (viewType)
      {
         NoteHeader ->
         {
            val view = inflater.inflate(R.layout.note, parent, false)
            NoteViewHolder(view) { position -> onItemSelected(position) }
         }
         else ->
         {
            val view = inflater.inflate(R.layout.note_options, parent, false)
            NoteOptionsViewHolder(view)
         }
      }
   }

   override fun onBindViewHolder(
      holder: RecyclerView.ViewHolder,
      position: Int
   )
   {

   }

   override fun getItemCount(): Int
   {
      return itemTypeList.size
   }

   override fun getItemViewType(position: Int): Int
   {
      return if (position < 0 || position >= itemCount) None else itemTypeList[position]
   }

   private fun onItemSelected(position: Int)
   {
      val itemOneBelowPos = position + 1
      when (getItemViewType(itemOneBelowPos))
      {
         NoteOptions ->
         {
            // If an options menu is below the item,
            // we want to 'close' it by removing it
            itemTypeList.removeAt(itemOneBelowPos)
            notifyItemRemoved(itemOneBelowPos)
         }
         else ->
         {
            // If either another header is below the
            // item, or we are at the end of the list,
            // we want to 'expand' the item by adding
            // an options menu
            itemTypeList.add(itemOneBelowPos, NoteOptions)
            notifyItemInserted(itemOneBelowPos)
         }
      }
   }

   companion object
   {
      private const val None = 0
      private const val NoteHeader = 1
      private const val NoteOptions = 2
   }
}
