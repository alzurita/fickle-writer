package com.zurita.ficklewriter.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.NotesContainerFragmentBinding
import com.zurita.ficklewriter.model.NotesViewModel
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

   private val viewModel by activityViewModels<NotesViewModel>()

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
      binding.pager.adapter = NotePageAdapter(layoutInflater, viewModel.notes)
      activity?.let {
         viewModel.lastNoteAdded.observe(it) { note -> onNoteAdded(note) }
         viewModel.foregroundPosition.observe(it) { position -> onNoteBringToForeground(position) }
      }
   }

   private fun onNoteBringToForeground(position: Int)
   {
      if(position >= 0)
         binding.pager.setCurrentItem(position, true)
   }

   override fun onAttach(context: Context)
   {
      super.onAttach(context)
      viewModel.notesContainerCreated.postValue(true)
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
