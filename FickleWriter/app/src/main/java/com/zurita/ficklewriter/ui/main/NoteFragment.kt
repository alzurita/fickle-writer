package com.zurita.ficklewriter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zurita.ficklewriter.databinding.NoteFragmentBinding

class NoteFragment : Fragment()
{
   companion object
   {
      fun newInstance() = NoteFragment()
   }

   private var _binding: NoteFragmentBinding? = null

   /**
    * This property is only valid between onCreateView and
    * onDestroyView.
    */
   private val binding get() = _binding!!

   private var title = "Empty"

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      title = activity?.intent?.getStringExtra("com.zurita.ficklewriter.NoteTitle") ?: "Still empty"
      _binding = NoteFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   )
   {
      super.onViewCreated(view, savedInstanceState)
      //binding.textView.text = title
   }
}
