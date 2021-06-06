package com.zurita.ficklewriter.ui.main

import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.ITALIC
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.ChapterFragmentBinding
import com.zurita.ficklewriter.ui.editor.EditTitleDialog
import com.zurita.ficklewriter.ui.editor.EditTitleDialogListener
import com.zurita.ficklewriter.ui.format.AlignmentMarkdownFormatter
import com.zurita.ficklewriter.ui.format.MarkdownFormatter
import com.zurita.ficklewriter.ui.format.TextReplaceFormatter
import com.zurita.ficklewriter.ui.format.TypefaceMarkdownFormatter

class ChapterFragment
   : Fragment(),
     EditTitleDialogListener
{
   private var _binding: ChapterFragmentBinding? = null
   /** Chapter binding to interact with underlying View
    * Only valid between onCreateView and onDestroyView. */
   private val binding get() = _binding!!

   private var _sourceCodeTypeface: Typeface? = null
   /** Font used for displaying plaintext with markup */
   private val sourceCodeTypeface get() = _sourceCodeTypeface!!

   private var _textNormalTypeface: Typeface? = null
   /** Font normally displayed in edit text */
   private val textNormalTypeface get() = _textNormalTypeface

   /** True when markup is visible on the editor */
   private var markupTextIsVisible = false

   /**
    * After considering dynamically formatting the text as we go,
    * I think that it doesn't make sense to have that. It will make
    * errors and does feel kind of jerky. Also, it limits the types
    * of formatters that can be used because they can interfere
    * with each other.
    */
   private val fullFormatters = listOf<MarkdownFormatter>(
      TypefaceMarkdownFormatter(specialChars = "**", BOLD), /** Needs to go before * */
      TypefaceMarkdownFormatter(specialChars = "__", BOLD), /** Needs to go before _ */
      TypefaceMarkdownFormatter(specialChars = "*", ITALIC),
      TypefaceMarkdownFormatter(specialChars = "_", ITALIC),
      AlignmentMarkdownFormatter(specialChars = "~"),
      TextReplaceFormatter(textBefore = "---", textAfter = "\u2014"), /** Needs to go before -- */
      TextReplaceFormatter(textBefore = "--", textAfter = "\u2013")
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

      setupToolbar()

      registerTextWatchers()

      _sourceCodeTypeface = resources.getFont(R.font.source_code_pro_family)
      _textNormalTypeface = binding.text.typeface
   }

   private fun setupToolbar()
   {
      binding.toolbar.inflateMenu(R.menu.chapter_options)
      binding.toolbar.title = "Placeholder title"
      binding.toolbar.setOnMenuItemClickListener { menuItem ->
         var clickHandled = true

         when (menuItem.itemId)
         {
            R.id.edit_title -> editTitle(menuItem)
            R.id.toggle_markup -> toggleMarkup(menuItem)
            else -> clickHandled = false
         }

         clickHandled
      }
   }

   private fun registerTextWatchers()
   {
      // Nothing to do for now
      // Keeping it anyway because I'll probably
      // use it for triggering saves
   }

   private fun unregisterTextWatchers()
   {
      // Nothing to do for now
   }

   private fun toggleMarkup(menuItem: MenuItem)
   {
      unregisterTextWatchers()

      markupTextIsVisible = !markupTextIsVisible
      menuItem.setTitle(
         if (markupTextIsVisible) R.string.hide_markup
         else R.string.show_markup
      )

      if (markupTextIsVisible)
      {
         for (formatter in fullFormatters)
               formatter.spansToMarkdown(binding.text.text)

         binding.text.typeface = sourceCodeTypeface
      }
      else
      {
         for (formatter in fullFormatters)
               formatter.markdownToSpans(binding.text.text)

         binding.text.typeface = textNormalTypeface
      }

      registerTextWatchers()
   }

   private fun editTitle(menuItem: MenuItem?)
   {
      val dialog = EditTitleDialog(
         binding.toolbar.title,
         this
      )
      dialog.show(parentFragmentManager, "EditTitleDialog")
   }

   override fun onTitleEdited(title: String)
   {
      binding.toolbar.title = title
   }
}
