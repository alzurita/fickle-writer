package com.zurita.ficklewriter.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.MainFragmentBinding
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.model.NotesContainerViewModel
import com.zurita.ficklewriter.ui.editor.EditorAdapter
import com.zurita.ficklewriter.ui.editor.EditorAdapterListener
import com.zurita.ficklewriter.ui.main.CustomIntent.DATA_NOTE

class MainFragment
   : Fragment(),
     EditorAdapterListener
{
   private var _binding: MainFragmentBinding? = null
   /** Editor binding to interact with underlying View
    * Only valid between onCreateView and onDestroyView. */
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      _binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   )
   {
      super.onViewCreated(view, savedInstanceState)
      binding.toolbar.inflateMenu(R.menu.toolbar)

      setupAdapter()
   }

   private fun setupAdapter()
   {
      binding.mainPanel?.notesList?.adapter =
            EditorAdapter(layoutInflater,this)

      binding.mainPanel?.notesList?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
   }

   override fun onChapterSelected(note: Note?)
   {
      val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
      if(navController.currentDestination?.id == R.id.editorFragment)
      {
         val directions = MainFragmentDirections.actionEditorFragmentToChapterFragment()
         navController.navigate(directions)
      }
   }

   override fun onPinSelected(note: Note)
   {
      val intent = Intent(context, NotesContainerActivity::class.java).apply {
         putExtra(DATA_NOTE, note)
      }
      context?.startActivity(intent)
   }

   override fun onEditSelected(note: Note)
   {
      val viewModel by activityViewModels<NotesContainerViewModel>()
      viewModel.addNote(note)

      val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
      if(navController.currentDestination?.id == R.id.editorFragment)
      {
         val directions = MainFragmentDirections.actionEditorFragmentToNotesContainerFragment()
         navController.navigate(directions)
      }
   }
}
