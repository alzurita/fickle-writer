package com.zurita.ficklewriter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.NoteFragmentBinding

class NoteFragment(
   private val note: Note?
) : Fragment()
{
   companion object
   {
      fun newInstance(note: Note? = null) = NoteFragment(note)
   }

   private var _binding: NoteFragmentBinding? = null

   /**
    * This property is only valid between onCreateView and
    * onDestroyView.
    */
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      _binding = NoteFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   )
   {
      super.onViewCreated(view, savedInstanceState)
      binding.editText.text?.clear()
      binding.editText.text?.append(note?.title ?: "Empty Note")
   }

   override fun onSaveInstanceState(outState: Bundle)
   {
      super.onSaveInstanceState(outState)
      outState.putString("title", note?.title ?: "")
   }

   override fun onViewStateRestored(savedInstanceState: Bundle?)
   {
      super.onViewStateRestored(savedInstanceState)
      val title = savedInstanceState?.getString("title") ?: "no title"
      binding.editText.text?.clear()
      binding.editText.text?.append(title)
   }
}