package com.zurita.ficklewriter.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.ui.editor.EditorFragment
import com.zurita.ficklewriter.ui.editor.NoteFragment

class MainActivity : AppCompatActivity()
{

   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.main_activity)

      if(intent.action == CustomIntent.VIEW_PINNED_NOTE)
      {
         supportFragmentManager.beginTransaction()
               .replace(R.id.container, NoteFragment.newInstance())
               .commitNow()
      }
      else if (savedInstanceState == null)
      {
         supportFragmentManager.beginTransaction()
               .replace(R.id.container, EditorFragment.newInstance())
               .commitNow()
      }

      // The first activity to call the view model will
      // create it. All subsequent calls from activities/fragments
      // will use the same model.

      createNotificationChannel()
   }

   private fun createNotificationChannel()
   {
      // Create the NotificationChannel, but only on API 26+ because
      // the NotificationChannel class is new and not in the support library
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
      {
         val name = getString(R.string.notification_channel_name)
         val descriptionText = getString(R.string.notification_channel_description)
         val importance = NotificationManager.IMPORTANCE_DEFAULT
         val pinsChannel = resources.getString(R.string.pins_channel)
         val channel = NotificationChannel(pinsChannel, name, importance).apply {
            description = descriptionText
         }
         // Register the channel with the system
         val notificationManager: NotificationManager =
               getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         notificationManager.createNotificationChannel(channel)
      }
   }
}
