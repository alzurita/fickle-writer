package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.util.Log
import android.util.Range
import java.lang.Integer.max

class TypefaceMarkdownFormatter(
   private val specialChars: CharSequence,
   private val typeface: Int
) : TextWatcher
{
   private var formatRange: Range<Int>? = null
   private val lengthSpecialChars = specialChars.length
   private val specialCharsStr = specialChars.toString()

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

      val startOfCheck = max(start - (lengthSpecialChars-count), 0)
      val endOfCheck = startOfCheck + lengthSpecialChars

      if(endOfCheck <= s.length)
      {
         val charsInQuestion = s.subSequence(startOfCheck, endOfCheck)
         if (charsInQuestion.contentEquals(specialChars))
         {
            val firstOccurrence = s.indexOf(specialCharsStr)
            val lastOccurrence = s.lastIndexOf(specialCharsStr)

            if (firstOccurrence >= 0 && lastOccurrence > 0 &&
                firstOccurrence != lastOccurrence
            )
            {
               formatRange = Range(firstOccurrence, lastOccurrence)
            }
         }
      }
   }

   override fun afterTextChanged(s: Editable?)
   {
      if (formatRange == null) return

      formatRange?.let {
         formatRange = null

         if(it.lower != it.upper)
         {
            s?.setSpan(
               StyleSpan(typeface), it.lower, it.upper,
               Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
         }
         s?.replace(it.lower, it.lower + lengthSpecialChars, "")
         s?.replace(it.upper-1, it.upper-1 + lengthSpecialChars, "")
      }
   }
}