package com.zurita.ficklewriter.ui.main

data class Note(
   var title: String = "",
   var shortDescription: String = "",
   val id: Int = 0,
   var pinned: Boolean = false
)
