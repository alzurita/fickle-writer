package com.zurita.ficklewriter.ui.main

import android.graphics.Typeface.BOLD
import android.graphics.Typeface.ITALIC
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zurita.ficklewriter.databinding.ChapterFragmentBinding
import com.zurita.ficklewriter.ui.editor.format.AlignmentMarkdownFormatter
import com.zurita.ficklewriter.ui.editor.format.TextReplaceFormatter
import com.zurita.ficklewriter.ui.editor.format.TypefaceMarkdownFormatter

class ChapterFragment : Fragment()
{
   companion object
   {
      /**
       * The API documentation recommends following this pattern
       * because it allows you to vertically separate the fragment
       * from it’s hosting activity. It keeps your application
       * components modular.
       */
      fun newInstance() = ChapterFragment()
   }

   private var _binding: ChapterFragmentBinding? = null

   /** Chapter binding to interact with underlying View
    * Only valid between onCreateView and onDestroyView. */
   private val binding get() = _binding!!

   /** List of objects that format markup text as soon as it
    * is entered. It isn't sophisticated, instead trading off
    * simplicity for some possible error.
    * I think this is the best it's going to be, because there's
    * probably a speed hit if I try to scan the whole text each time.
    */
   private val formatters = listOf(
      TypefaceMarkdownFormatter(specialChars = "*", BOLD),
      TypefaceMarkdownFormatter(specialChars = "_", ITALIC),
      AlignmentMarkdownFormatter(specialChars = "~"),
      TextReplaceFormatter(textBefore = "--", textAfter = "\u2013"),
      TextReplaceFormatter(textBefore = "\u2013-", textAfter = "\u2014")
   )

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View
   {
      _binding = ChapterFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   )
   {
      super.onViewCreated(view, savedInstanceState)

      for(formatter in formatters)
      {
         binding.text.addTextChangedListener(formatter)
      }
   }
}