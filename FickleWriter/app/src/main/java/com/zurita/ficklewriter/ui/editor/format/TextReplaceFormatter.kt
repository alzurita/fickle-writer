package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.TextWatcher
import android.util.Range
import java.lang.Integer.max

class TextReplaceFormatter(
   private val textBefore: CharSequence,
   private val textAfter: CharSequence
) : TextWatcher
{
   private var formatRange: Range<Int>? = null
   private var lengthBefore = textBefore.length

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
      if (s == null) return

      val startOfCheck = max(start - (lengthBefore - count), 0)
      val endOfCheck = startOfCheck + lengthBefore

      if (endOfCheck <= s.length)
      {
         val charsInQuestion = s.subSequence(startOfCheck, endOfCheck)
         if (charsInQuestion.contentEquals(textBefore))
         {
            formatRange = Range(startOfCheck, endOfCheck)
         }
      }
   }

   override fun afterTextChanged(s: Editable?)
   {
      if (formatRange == null) return

      formatRange?.let {
         formatRange = null

         s?.replace(it.lower, it.upper, textAfter)
      }
   }
}
