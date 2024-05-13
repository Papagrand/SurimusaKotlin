package com.example.surimusakotlin.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val highres: String?,
    val is_user_uploaded: Boolean,
    val thumb: String?
): Parcelable