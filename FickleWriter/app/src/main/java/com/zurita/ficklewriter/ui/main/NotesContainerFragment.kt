package com.zurita.ficklewriter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zurita.ficklewriter.databinding.NoteFragmentBinding
import com.zurita.ficklewriter.databinding.NotesContainerFragmentBinding

class NotesContainerFragment : Fragment()
{
   companion object
   {
      fun newInstance() = NotesContainerFragment()
   }

   private var _binding: NotesContainerFragmentBinding? = null
   private val binding: NotesContainerFragmentBinding get()
   {
      return _binding!!
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      _binding = NotesContainerFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }
}
