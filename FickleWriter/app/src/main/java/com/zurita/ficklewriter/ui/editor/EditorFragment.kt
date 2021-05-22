package com.zurita.ficklewriter.ui.editor

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

   private var _pinIcon: IconCompat? = null
   /** Icon that is displayed on each pin
    * Only valid between onCreateView and onDestroyView. */
   private val pinIcon get() = _pinIcon!!

   /**
    * List of FABs, one for each Note that has been pinned.
    * The first pin is shown initially, and it can be dragged
    * around to different locations on this Fragment. When
    * selected, all pins are shown in a row at the top.
    *
    * &nbsp;
    *
    * This list naturally has a maximum length based on the
    * how many can reasonably fit within the width of the screen.*/
   private val listOfPins = mutableListOf<FloatingActionButton>()

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
      val pin = FloatingActionButton(context!!)
      binding.coordinatorLayout?.addView(pin)
      listOfPins.add(pin)

      with(pin.layoutParams as CoordinatorLayout.LayoutParams)
      {
         anchorId = R.id.toolbar
         anchorGravity = Gravity.BOTTOM or Gravity.START
      }

      setPinVisibility()
   }

   override fun unpinNote(note: Note)
   {
      if(listOfPins.isNotEmpty())
      {
         binding.coordinatorLayout?.removeView(listOfPins.first())
         listOfPins.removeFirst()
      }

      setPinVisibility()
   }

   private fun setPinVisibility()
   {
      if(listOfPins.isNotEmpty())
      {
         listOfPins.first().visibility = VISIBLE
         for(pin in listOfPins.subList(1, listOfPins.size))
         {
            pin.visibility = GONE
         }
      }
   }
}
