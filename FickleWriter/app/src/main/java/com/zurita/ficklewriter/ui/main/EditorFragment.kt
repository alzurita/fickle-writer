package com.zurita.ficklewriter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.EditorFragmentBinding

class EditorFragment : Fragment()
{

   companion object
   {
      fun newInstance() = EditorFragment()
   }

   private var _binding: EditorFragmentBinding? = null

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

      binding.mainPanel?.notesList?.adapter = NoteViewAdapter(layoutInflater, context!!)
      binding.mainPanel?.notesList?.layoutManager =
            LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
   }
}