package com.zurita.ficklewriter.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.drawable.IconCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.EditorFragmentBinding
import com.zurita.ficklewriter.ui.editor.EditorViewModel
import com.zurita.ficklewriter.ui.editor.Note
import com.zurita.ficklewriter.ui.editor.NoteViewAdapter
import com.zurita.ficklewriter.ui.editor.NoteViewAdapterListener

class EditorFragment
   : Fragment(),
     NoteViewAdapterListener
{
   companion object
   {
      /**
       * The API documentation recommends following this pattern
       * because it allows you to vertically separate the fragment
       * from it’s hosting activity. It keeps your application
       * components modular.
       */
      fun newInstance() = EditorFragment()
   }

   private var _binding: EditorFragmentBinding? = null
   /** Editor binding to interact with underlying View
    * Only valid between onCreateView and onDestroyView. */
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      _binding = EditorFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   )
   {
      super.onViewCreated(view, savedInstanceState)
      binding.toolbar.inflateMenu(R.menu.toolbar)

      val model by activityViewModels<EditorViewModel>()

      setupAdapter()
   }

   private fun setupAdapter()
   {
      binding.mainPanel?.notesList?.adapter =
            NoteViewAdapter(layoutInflater, context!!, this)

      binding.mainPanel?.notesList?.layoutManager =
            LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
   }

   override fun pinNote(note: Note)
   {
      val intent = Intent(context, NoteActivity::class.java).apply {
         putExtra("note", note)
      }
      context?.startActivity(intent)
   }
}