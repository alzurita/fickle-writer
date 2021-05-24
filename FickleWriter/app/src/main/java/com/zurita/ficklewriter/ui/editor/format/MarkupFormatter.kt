package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

class MarkupFormatter<T>(
   private val specialChars: String,
   private val spanType: Class<T>,
   private val createSpan: () -> T,
   private val isCorrectType: (T) -> Boolean
)
{
   fun spansToMarkup(editable: Editable?)
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

   fun markupToSpans(editable: Editable?)
   {
      if(editable == null) return

      var searchStart = 0
      while(true)
      {
         searchStart = editable.indexOf(specialChars, searchStart)
         if(searchStart < 0) break

         val searchEnd = editable.indexOf(specialChars, searchStart+1)
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
}
