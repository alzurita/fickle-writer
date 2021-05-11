package com.zurita.ficklewriter.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.R

class NoteViewAdapter(
   private val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   private val itemTypeList = mutableListOf(
      SummarySection,  SummaryHeader, NoteSection, NoteHeader, NoteHeader, NoteHeader,
      ChapterSection, ChapterHeader, ChapterHeader, End
   )

   // Dummy items because I don't have objects for these
   private val summary = listOf(SummaryHeader)
   private val notes = mutableListOf(NoteHeader, NoteHeader, NoteHeader)
   private val chapters = mutableListOf(ChapterHeader, ChapterHeader, ChapterHeader, ChapterHeader)

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): RecyclerView.ViewHolder
   {
      return when (viewType)
      {
         SummarySection ->
         {
            val view = inflater.inflate(R.layout.section_header, parent, false)
            SummarySectionViewHolder(view) { position -> onSectionSelected(position) }
         }
         SummaryHeader ->
         {
            val view = inflater.inflate(R.layout.story_overview, parent, false)
            SummaryHeaderViewHolder(view)
         }
         NoteSection ->
         {
            val view = inflater.inflate(R.layout.section_header, parent, false)
            NoteSectionViewHolder(view) { position -> onSectionSelected(position) }
         }
         NoteHeader ->
         {
            val view = inflater.inflate(R.layout.note, parent, false)
            NoteViewHolder(view) { position -> onNoteHeaderSelected(position) }
         }
         NoteOptions ->
         {
            val view = inflater.inflate(R.layout.note_options, parent, false)
            NoteOptionsViewHolder(view)
         }
         ChapterSection ->
         {
            val view = inflater.inflate(R.layout.section_header, parent, false)
            ChapterSectionViewHolder(view) { position -> onSectionSelected(position) }
         }
         ChapterHeader ->
         {
            val view = inflater.inflate(R.layout.chapter_overview, parent, false)
            ChapterViewHolder(view)
         }
         else ->
         {
            val view = View(parent.context)
            EndViewHolder(view)
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
      return if (position < 0 || position >= itemCount) Start else itemTypeList[position]
   }

   private fun onSectionSelected(position: Int)
   {
      if(position < 0)
         return

      val itemType = getItemViewType(position)
      val startOfSection = position + 1
      val endOfSection = itemTypeList.indexOf(
         when(itemType)
         {
            SummarySection -> SummaryEnd
            NoteSection -> NoteEnd
            ChapterSection -> ChapterEnd
            else -> End
         }
      )
      if(startOfSection == endOfSection)
      {
         val items : List<Int> =
               when(itemType)
               {
                  SummarySection -> summary
                  NoteSection -> notes
                  ChapterSection -> chapters
                  else -> listOf()
               }

         itemTypeList.addAll(startOfSection, items)
         notifyItemRangeInserted(startOfSection, items.size)
      }
      else
      {
         for(index in (startOfSection until endOfSection).reversed())
         {
            itemTypeList.removeAt(index)
         }
         notifyItemRangeRemoved(startOfSection, endOfSection - startOfSection)
      }
   }

   private fun onNoteHeaderSelected(position: Int)
   {
      if(position < 0)
         return

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
      private const val Start = 0

      private const val SummarySection = 1
      private const val SummaryHeader = 2
      private const val SummaryEnd = 10

      private const val NoteSection = 10
      private const val NoteHeader = 11
      private const val NoteOptions = 12
      private const val NoteEnd = 20

      private const val ChapterSection = 20
      private const val ChapterHeader = 21
      private const val ChapterEnd = 100

      private const val End = 100
   }
}
