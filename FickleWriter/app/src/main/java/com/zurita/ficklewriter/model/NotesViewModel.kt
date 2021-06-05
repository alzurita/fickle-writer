package com.zurita.ficklewriter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zurita.ficklewriter.data.Note

class NotesViewModel : ViewModel()
{
   val notes = mutableListOf<Note>()
   val notesContainerCreated = MutableLiveData(false)
   val lastNoteAdded = MutableLiveData<Note?>(null)

   fun addNote(note: Note?)
   {
      if(note == null)
         return

      notes.add(note)
      lastNoteAdded.postValue(note)
   }
}
