package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.Layout
import android.text.Spannable
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.TextWatcher
import android.text.style.AlignmentSpan
import android.util.Log
import android.util.Range

class AlignmentMarkdownFormatter(
   private val specialChars: CharSequence
) : TextWatcher,
    MarkdownFormatter
{
   private var formatRange: Range<Int>? = null

   private val specialCharsWithNewline = "$specialChars\n"

   override fun beforeTextChanged(
      s: CharSequence?,
      start: Int,
      count: Int,
      after: Int
   )
   {

   }

   override fun onTextChanged(
      s: CharSequence?,
      start: Int,
      before: Int,
      count: Int
   )
   {
      if (count == 1 && s != null && s[start] == '\n')
      {
         val prevNewline = s.subSequence(0, start).indexOfLast { c -> c == '\n' } + 1
         val line = s.subSequence(prevNewline, start)
         formatRange = (
               if (line.contentEquals(specialChars)) Range.create(prevNewline, start)
               else null)
      }
   }

   override fun afterTextChanged(s: Editable?)
   {
      if (formatRange == null) return

      formatRange?.let {
         s?.setSpan(
            createSpan(),
            it.lower, it.upper, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
         )
      }
      formatRange = null
   }

   override fun spansToMarkdown(editable: Editable?)
   {
      if(editable == null) return

      val spans = editable.getSpans(
         0, editable.length, createSpan().javaClass)

      for(span in spans)
      {
         if(span.alignment == Layout.Alignment.ALIGN_CENTER)
         {
            editable.removeSpan(span)
         }
      }
   }

   override fun markdownToSpans(editable: Editable?)
   {
      if(editable == null) return

      var spanStart = 0

      while(true)
      {
         spanStart = editable.indexOf(specialCharsWithNewline, spanStart)
         if(spanStart < 0) break

         editable.setSpan(
            createSpan(),
            spanStart,
            spanStart + specialCharsWithNewline.length,
            SPAN_EXCLUSIVE_EXCLUSIVE
         )

         spanStart += specialCharsWithNewline.length
      }
   }

   private fun createSpan(): AlignmentSpan.Standard
   {
      return AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
   }
}
