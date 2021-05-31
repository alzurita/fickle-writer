package com.zurita.ficklewriter.ui.editor

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zurita.ficklewriter.R

interface EditTitleDialogListener
{
   fun onTitleEdited(title: String)
}

class EditTitleDialog(
   private val title: CharSequence,
   private var listener: EditTitleDialogListener)
   : DialogFragment(),
     DialogInterface.OnClickListener
{
   private var editText: EditText? = null

   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
   {
      if(editText == null)
         editText = EditText(context)

      editText?.let {
         it.text.replace(0, it.text.length ?: 0, title)
         it.isSingleLine = true
         it.selectAll()
      }

      return activity?.let {
         AlertDialog.Builder(it)
               .setMessage(R.string.edit_title)
               .setCancelable(true)
               .setPositiveButton(R.string.ok, this)
               .setNegativeButton(R.string.cancel, this)
               .setView(editText)
               .create()
      } ?: throw IllegalStateException("Activity cannot be null")
   }

   override fun onClick(
      dialog: DialogInterface?,
      which: Int
   )
   {
      when(which)
      {
         BUTTON_POSITIVE ->
            listener.onTitleEdited(editText?.text.toString())
      }
   }
}