package com.zurita.ficklewriter.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE
import com.google.android.material.tabs.TabLayoutMediator
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.NotesContainerFragmentBinding
import com.zurita.ficklewriter.model.NotesContainerViewModel
import com.zurita.ficklewriter.ui.notepage.NotePageAdapter

/**
 * This fragment contains all the notes that have been pinned.
 */
class NotesContainerFragment : Fragment()
{
   companion object
   {
      fun newInstance() = NotesContainerFragment()
   }

   private var _binding: NotesContainerFragmentBinding? = null
   private val binding: NotesContainerFragmentBinding
      get()
      {
         return _binding!!
      }

   private val viewModel by activityViewModels<NotesContainerViewModel>()

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      _binding = NotesContainerFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   )
   {
      super.onViewCreated(view, savedInstanceState)

      binding.toolbar.inflateMenu(R.menu.chapter_options)

      setupTabPaging()

      activity?.let {
         viewModel.lastNoteAdded.observe(it) { note -> onNoteAdded(note) }
         viewModel.foregroundPosition.observe(it) { position -> onNoteBringToForeground(position) }
      }
   }

   private fun setupTabPaging()
   {
      binding.pager.adapter = NotePageAdapter(layoutInflater, viewModel.notes)

      if (viewModel.isInWindowedMode)
      {
         // Remove the toolbar and tabLayout if we are in windowed mode, because
         // there's not enough room for it
         binding.toolbar.visibility = GONE
         binding.tabLayout.visibility = GONE
      }
      else
      {
         // This allows for longer note titles and more tabs without
         // squishing them all together
         binding.tabLayout.tabMode = MODE_SCROLLABLE

         setupTabLayoutMediator()
      }
   }

   /**
    * If the tabLayout is visible, sync it with the
    * page adapter, so the pages match with the tab titles
    */
   private fun setupTabLayoutMediator()
   {
      TabLayoutMediator(binding.tabLayout, binding.pager
      ) { tab, position ->
         if(position < viewModel.notes.size)
            tab.text = viewModel.notes[position].title
      }.attach()
   }

   private fun onNoteBringToForeground(position: Int)
   {
      if(position >= 0)
         binding.pager.setCurrentItem(position, true)
   }

   private fun onNoteAdded(note: Note?)
   {
      if (note == null)
         return

      // Cannot call notifyItemInserted() because of a threading
      // bug in RecyclerView when used with a FragmentStateAdapter.
      // Instead, must reset data set each time. Alternative is
      // to catch the exception thrown by the LayoutManager by creating
      // a wrapper around it.
      // @see https://tinyurl.com/4xft7ar5
      binding.pager.adapter?.notifyDataSetChanged()
   }
}
