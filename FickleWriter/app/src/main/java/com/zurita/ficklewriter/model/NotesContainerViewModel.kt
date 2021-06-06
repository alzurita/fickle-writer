package com.zurita.ficklewriter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zurita.ficklewriter.data.Note

/**
 * ViewModel connecting the NotesContainerFragment to its parent Activity.
 */
class NotesContainerViewModel : ViewModel()
{
   /**
    * This live data is used to allow the NotesContainerFragment to notify
    * when its pager is initialized and ready to be synced to if the UI includes
    * a tab header.
    */
   val notesContainerCreated = MutableLiveData(false)

   private val _notes = mutableListOf<Note>()
   /** List of pinned notes */
   val notes = _notes as List<Note>

   private val _lastNoteAdded = MutableLiveData<Note?>(null)
   /** The latest note that has been added to the list. Null if the list is empty. */
   val lastNoteAdded = _lastNoteAdded as LiveData<Note?>

   private val _foregroundPosition = MutableLiveData(-1)
   /** The note that is currently in the foreground */
   val foregroundPosition = _foregroundPosition as LiveData<Int>

   /**
    * If the note does not exist in the list, it is added.
    * Listeners will be notified that the last note added has
    * changed.
    *
    * &nbsp
    *
    * The pinned note will be sent to the foreground, and
    * listeners to the foreground position will be notified.
    *
    * @param note note that will be added and brought to the foreground
    */
   fun addNote(note: Note?)
   {
      if(note == null)
         return

      if(note !in notes)
      {
         _notes.add(note)
         _lastNoteAdded.postValue(note)
      }

      _foregroundPosition.postValue(notes.indexOf(note))
   }
}
