package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.Layout
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.AlignmentSpan
import android.util.Range

class AlignmentMarkdownFormatter(
   private val specialChars: CharSequence
) : TextWatcher
{
   private var formatRange: Range<Int>? = null

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
         val prevNewline = s.subSequence(0, start)
                                 .indexOfLast { c -> c == '\n' } + 1
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
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
            it.lower, it.upper, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
         )
      }
      formatRange = null
   }
}
