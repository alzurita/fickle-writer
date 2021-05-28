package com.zurita.ficklewriter.ui.editor.format

import android.text.Editable
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

interface MarkdownFormatter
{
   fun spansToMarkdown(editable: Editable?)
   fun markdownToSpans(editable: Editable?)
}
