package com.zurita.ficklewriter.ui.notepage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zurita.ficklewriter.ui.main.NotesContainerFragment

class NotePageAdapter(
   fragmentManager: FragmentManager,
   lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle)
{
   override fun getItemCount(): Int
   {
      return 3
   }

   override fun createFragment(position: Int): Fragment = NotesContainerFragment.newInstance()
}