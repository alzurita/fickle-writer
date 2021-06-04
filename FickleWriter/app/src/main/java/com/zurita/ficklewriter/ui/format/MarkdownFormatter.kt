package com.zurita.ficklewriter.ui.format

import android.text.Editable

interface MarkdownFormatter
{
   fun spansToMarkdown(editable: Editable?)
   fun markdownToSpans(editable: Editable?)
}
