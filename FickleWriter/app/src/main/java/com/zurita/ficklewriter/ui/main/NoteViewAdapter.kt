package com.zurita.ficklewriter.ui.main

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.content.ContextCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.recyclerview.widget.RecyclerView
import com.zurita.ficklewriter.MainActivity
import com.zurita.ficklewriter.R

class NoteViewAdapter(
   private val inflater: LayoutInflater,
   private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   /**
    * Each AdapterItem has a type to mark what kind of
    * data is located at a given index of the list. Certain
    * types of items have actual data associated with them.
    * Others that do not have data are used to mark sections.
    */
   data class AdapterItem(
      val type: Int,
      val data: Any? = null
   )

   /** Each type of item is listed in order based on its section */
   private val itemTypeList = mutableListOf(
      AdapterItem(SummarySection), AdapterItem(NoteSection), AdapterItem(ChapterSection),
      AdapterItem(End)
   )

   /** There is only one summary item, but it is still a list */
   private val summaryHeader = AdapterItem(SummaryHeader)

   /** Collection of notes */
   private val notes = mutableListOf(
      Note("Note 1", "A short description of the first note."),
      Note("Note 2", "Some different description of the second note."),
      Note("Note 3", "And a third for the third.")
   )

   /** Collection of chapters */
   private val chapters = mutableListOf(ChapterHeader, ChapterHeader, ChapterHeader, ChapterHeader)

   /** Pins channel, must be previously registered */
   private val pinsChannel = context.resources.getString(R.string.pins_channel)

   /** Pins group, all notifications will get added into one group */
   private val pinsGroup = context.resources.getString(R.string.pins_group)

   private val notificationSummary =
         NotificationCompat.Builder(context, pinsChannel).setSmallIcon(R.drawable.ic_tea_cup_image)
               .setContentTitle(context.resources.getString(R.string.notes))
               .setPriority(NotificationCompat.PRIORITY_LOW).setGroup(pinsGroup)
               .setGroupSummary(true).build()

   init
   {
      // Populate the list with a set of enums for notes and chapters
      with(itemTypeList) {
         add(indexOfType(SummaryEnd), summaryHeader)
         addAll(indexOfType(NoteEnd), List(notes.size) { AdapterItem(NoteHeader, Note()) })
         addAll(indexOfType(ChapterEnd), List(chapters.size) { AdapterItem(ChapterHeader) })
      }
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
   ): RecyclerView.ViewHolder
   {
      return when (viewType)
      {
         SummarySection ->
         {
            val view = inflater.inflate(R.layout.section_header, parent, false)
            SummarySectionViewHolder(view) { position -> onSectionSelected(position) }
         }
         SummaryHeader ->
         {
            val view = inflater.inflate(R.layout.story_overview, parent, false)
            SummaryHeaderViewHolder(view)
         }
         NoteSection ->
         {
            val view = inflater.inflate(R.layout.section_header, parent, false)
            NoteSectionViewHolder(view) { position -> onSectionSelected(position) }
         }
         NoteHeader ->
         {
            val view = inflater.inflate(R.layout.note, parent, false)
            NoteViewHolder(view) { position -> onNoteHeaderSelected(position) }
         }
         NoteOptions ->
         {
            val view = inflater.inflate(R.layout.note_options, parent, false)
            NoteOptionsViewHolder(view) { note, position -> onPinSelected(note, position) }
         }
         ChapterSection ->
         {
            val view = inflater.inflate(R.layout.section_header, parent, false)
            ChapterSectionViewHolder(view) { position -> onSectionSelected(position) }
         }
         ChapterHeader ->
         {
            val view = inflater.inflate(R.layout.note, parent, false)
            ChapterViewHolder(view)
         }
         else ->
         {
            val view = View(parent.context)
            EndViewHolder(view)
         }
      }
   }

   private fun onPinSelected(
      note: Note,
      position: Int
   )
   {
      // Create bubble intent
      val target = Intent(context, MainActivity::class.java)
      val bubbleIntent = PendingIntent.getActivity(context, 0, target, 0)

      // Create bubble metadata
      val bubbleData = NotificationCompat.BubbleMetadata.Builder().setIcon(
         IconCompat.createWithResource(context, R.drawable.ic_tea_cup_image))
            .setDesiredHeight(600).setIntent(bubbleIntent).build()

      // Fake person
      val chatPartner = Person.Builder()
            .setName("Chat partner")
            .setImportant(true)
            .setIcon(
               IconCompat.createWithResource(context, R.drawable.ic_tea_cup_image))
            .build()

      target.action = "ACTION_VIEW"
      // Shortcut
      val shortcutId = "Shorty"
      val shortcut = ShortcutInfoCompat.Builder(context,shortcutId)
            .setActivity(ComponentName(context, MainActivity::class.java))
            .setLongLived(true)
            .setPerson(chatPartner)
            .setShortLabel("Hello World")
            .setIntent(target)
            .build()

      val list = java.util.ArrayList<ShortcutInfoCompat>().also { it.add(shortcut) }
      ShortcutManagerCompat.addDynamicShortcuts(context, list)

      // Create notification
      val builder = NotificationCompat.Builder(context, pinsChannel)
            .setContentIntent(bubbleIntent)
            .setSmallIcon(R.drawable.ic_tea_cup_image)
            .setBubbleMetadata(bubbleData)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setShortcutId(shortcutId)
            .setStyle(NotificationCompat.MessagingStyle(chatPartner))
            .setContentText("Hello World")
            .setShowWhen(true)

      with(NotificationManagerCompat.from(context)) {
         notify(10, builder.build())
      }

//      val notification = NotificationCompat.Builder(context, pinsChannel)
//            .setSmallIcon(R.drawable.ic_tea_cup_image).setContentTitle(note.title)
//            .setContentText(note.shortDescription).setPriority(NotificationCompat.PRIORITY_LOW)
//            .setGroup(pinsGroup).build()
//
//      with(NotificationManagerCompat.from(context)) {
//         // notificationId is a unique int for each notification that you must define
//         notify(getNoteIndex(NoteOptions, position), notification)
//         notify(-1, notificationSummary)
//      } // end with
   }

   override fun onBindViewHolder(
      holder: RecyclerView.ViewHolder,
      position: Int
   )
   {
      when (val itemType = itemTypeList[position].type)
      {
         NoteHeader, NoteOptions ->
         {
            val noteIndex = getNoteIndex(itemType, position)

            val noteToBind = notes[noteIndex]
            (holder as NoteViewHolderIntf).bind(noteToBind)
         }
      }
   }

   private fun getNoteIndex(
      itemType: Int,
      position: Int
   ): Int
   {
      return itemTypeList.subList(itemTypeList.indexOfType(NoteSection), position)
                   .count { adapterItem -> adapterItem.type == NoteHeader } - if (itemType == NoteOptions) 1 else 0
   }

   override fun getItemCount(): Int
   {
      return itemTypeList.size
   }

   override fun getItemViewType(position: Int): Int
   {
      return if (position < 0 || position >= itemCount) Start else itemTypeList[position].type
   }

   private fun onSectionSelected(position: Int)
   {
      if (position < 0) return

      val itemType = getItemViewType(position)
      val itemEndType = getItemEndType(itemType)

      val startOfSection = position + 1
      val endOfSection = itemTypeList.indexOfType(itemEndType)
      if (startOfSection == endOfSection)
      {
         val adapterList = getAdapterList(itemType)
         itemTypeList.addAll(startOfSection, adapterList)
         notifyItemRangeInserted(startOfSection, adapterList.size)
      }
      else
      {
         for (index in (startOfSection until endOfSection).reversed())
         {
            itemTypeList.removeAt(index)
         }
         notifyItemRangeRemoved(startOfSection, endOfSection - startOfSection)
      }
   }

   private fun getAdapterList(itemType: Int): List<AdapterItem>
   {
      return (when (itemType)
      {
         SummarySection -> listOf(summaryHeader)
         NoteSection -> List(notes.size) { AdapterItem(NoteHeader, Note()) }
         ChapterSection -> List(chapters.size) { AdapterItem(ChapterHeader, null) }
         else -> listOf()
      })
   }

   private fun getItemEndType(itemType: Int): Int
   {
      return (when (itemType)
      {
         SummarySection -> SummaryEnd
         NoteSection -> NoteEnd
         ChapterSection -> ChapterEnd
         else -> End
      })
   }

   private fun onNoteHeaderSelected(position: Int)
   {
      // If you click really fast, you can get a (-1) index on
      // occasion since it's an invalid index
      if (position < 0) return

      val itemOneBelowPos = position + 1
      when (getItemViewType(itemOneBelowPos))
      {
         NoteOptions ->
         {
            // If an options menu is below the item,
            // we want to 'close' it by removing it
            itemTypeList.removeAt(itemOneBelowPos)
            notifyItemRemoved(itemOneBelowPos)
         }
         else ->
         {
            // If either another header is below the
            // item, or we are at the end of the list,
            // we want to 'expand' the item by adding
            // an options menu
            itemTypeList.add(itemOneBelowPos, AdapterItem(NoteOptions, itemTypeList[position].data))
            notifyItemInserted(itemOneBelowPos)
         }
      }
   }

   companion object
   {
      private const val Start = 0

      private const val SummarySection = 1
      private const val SummaryHeader = 2
      private const val SummaryEnd = 10

      private const val NoteSection = 10
      private const val NoteHeader = 11
      private const val NoteOptions = 12
      private const val NoteEnd = 20

      private const val ChapterSection = 20
      private const val ChapterHeader = 21
      private const val ChapterEnd = 100

      private const val End = 100
   }
}

private fun List<NoteViewAdapter.AdapterItem>.indexOfType(itemEndType: Int): Int
{
   return indexOf(find { adapterItem -> adapterItem.type == itemEndType })
}
