package com.zurita.ficklewriter.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE
import com.google.android.material.tabs.TabLayoutMediator
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.data.Note
import com.zurita.ficklewriter.databinding.NotesContainerActivityBinding
import com.zurita.ficklewriter.model.NotesViewModel
import com.zurita.ficklewriter.ui.main.CustomIntent.DATA_NOTE
import kotlinx.android.synthetic.main.notes_container_fragment.*

class NotesContainerActivity : AppCompatActivity()
{
   private var _binding: NotesContainerActivityBinding? = null
   private val binding: NotesContainerActivityBinding
      get()
      {
         return _binding!!
      }

   private val viewModel: NotesViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.notes_container_activity)
      _binding = NotesContainerActivityBinding.bind(findViewById(R.id.container))

      val note = intent?.getParcelableExtra<Note>(DATA_NOTE)
      viewModel.pinNote(note)

      if (isInMultiWindowMode)
      {
         // Remove the toolbar and tabLayout if we are in windowed mode, because
         // there's not enough room for it
         binding.toolbar.visibility = GONE
         binding.tabLayout.visibility = GONE
      }
      else
      {
         setSupportActionBar(binding.toolbar)

         // This allows for longer note titles and more tabs without
         // squishing them all together
         binding.tabLayout.tabMode = MODE_SCROLLABLE

         // Only care about waiting for the fragment to be
         // created if we need to sync with the tabLayout
         viewModel.notesContainerCreated.observe(this) { setupTabLayoutMediator(it) }
      }

      supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NotesContainerFragment.newInstance(), "taggy")
            .commitNow()
   }

   override fun onNewIntent(intent: Intent?)
   {
      super.onNewIntent(intent)

      val note = intent?.getParcelableExtra<Note>(DATA_NOTE)
      viewModel.pinNote(note)
   }

   /**
    * If the tabLayout is visible, sync it with the child fragment's
    * page adapter, so the pages match with the tab titles
    * @param layoutIsReady Once set to true, indicates that the fragment
    * is created and it's safe to be used to access its pager
    * @throws NullPointerException If the fragment or pager does not exist
    * @throws ClassCastException If the fragment found is not a NotesContainerFragment
    */
   private fun setupTabLayoutMediator(
      layoutIsReady: Boolean
   )
   {
      if(!layoutIsReady)
         return

      TabLayoutMediator(binding.tabLayout,
                        (supportFragmentManager.findFragmentByTag("taggy") as
                              NotesContainerFragment?)?.pager!!
      ) { tab, position ->
         if(position < viewModel.notes.size)
            tab.text = viewModel.notes[position].title
      }.attach()
   }
}
