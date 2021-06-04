package com.zurita.ficklewriter.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
   var title: String = "",
   var shortDescription: String = "",
   val id: Int = 0,
) : Parcelable