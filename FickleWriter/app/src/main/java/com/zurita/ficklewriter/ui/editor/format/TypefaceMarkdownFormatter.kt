package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.Spannable
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.util.Log
import android.util.Range
import java.lang.Integer.max

class TypefaceMarkdownFormatter(
   private val specialChars: CharSequence,
   private val typeface: Int
) : TextWatcher,
    MarkdownFormatter
{
   private var formatRange: Range<Int>? = null
   private val lengthSpecialChars = specialChars.length
   private val specialCharsStr = specialChars.toString()

   private val spanType = StyleSpan::class.java

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
               createSpan(), it.lower, it.upper,
               Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
         }
         s?.replace(it.lower, it.lower + lengthSpecialChars, "")
         s?.replace(it.upper-1, it.upper-1 + lengthSpecialChars, "")
      }
   }

   override fun spansToMarkdown(editable: Editable?)
   {
      if(editable == null) return

      val spans = editable.getSpans(0, editable.length, spanType)
      for(span in spans)
      {
         if(isCorrectType(span))
         {
            val start = editable.getSpanStart(span)
            val end = editable.getSpanEnd(span)
            editable.replace(end, end, specialChars)
            editable.replace(start, start, specialChars)
            editable.removeSpan(span)
         }
      }
   }

   override fun markdownToSpans(editable: Editable?)
   {
      if(editable == null) return

      var searchStart = 0
      while(true)
      {
         searchStart = editable.indexOf(specialCharsStr, searchStart)
         if(searchStart < 0) break

         val searchEnd = editable.indexOf(specialCharsStr, searchStart+1)
         if(searchEnd < 0) break

         editable.setSpan(
            createSpan(),
            searchStart,
            searchEnd,
            SPAN_EXCLUSIVE_EXCLUSIVE
         )

         editable.replace(searchEnd, searchEnd+specialChars.length, "")
         editable.replace(searchStart, searchStart+specialChars.length, "")

         searchStart = searchEnd+1
      }
   }

   private fun isCorrectType(span: StyleSpan?): Boolean
   {
      return span?.style == typeface
   }

   private fun createSpan(): Any
   {
      return StyleSpan(typeface)
   }


}