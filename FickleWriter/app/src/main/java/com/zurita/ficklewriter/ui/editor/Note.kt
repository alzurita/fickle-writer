package com.zurita.ficklewriter.ui.editor

data class Note(
   var title: String = "",
   var shortDescription: String = "",
   val id: Int = 0,
   var pinned: Boolean = false
)
