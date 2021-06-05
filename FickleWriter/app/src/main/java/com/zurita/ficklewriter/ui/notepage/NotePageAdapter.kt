package com.zurita.ficklewriter.ui.notepage

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView.NO_ID
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.ui.main.NoteFragment
import com.zurita.ficklewriter.ui.main.NotesContainerFragment

class NotePageAdapter(
   fragmentManager: FragmentManager,
   lifecycle: Lifecycle,
   private val notes: List<Note>
) : FragmentStateAdapter(fragmentManager, lifecycle)
{
   override fun getItemCount(): Int
   {
      return notes.size
   }

   override fun getItemId(position: Int): Long
   {
      return if(position < notes.size) 1 else NO_ID
   }

   override fun containsItem(itemId: Long): Boolean
   {
      return itemId.toInt() == 1
   }

   override fun createFragment(position: Int): Fragment
   {
      return NoteFragment.newInstance(notes[position])
   }
}