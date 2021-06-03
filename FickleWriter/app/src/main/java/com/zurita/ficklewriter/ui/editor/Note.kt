package com.zurita.ficklewriter.ui.editor

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
   var title: String = "",
   var shortDescription: String = "",
   val id: Int = 0,
) : Parcelable
