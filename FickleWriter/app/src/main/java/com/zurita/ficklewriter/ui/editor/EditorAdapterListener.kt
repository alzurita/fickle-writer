package com.zurita.ficklewriter.ui.editor

import com.zurita.ficklewriter.data.Note

interface EditorAdapterListener
{
   fun onChapterSelected(note: Note?)
   fun onPinSelected(note: Note)
   fun onEditSelected(note: Note)
}
