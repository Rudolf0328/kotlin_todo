package com.myapp.yongwoo.room.entity

import androidx.room.PrimaryKey

data class DoneItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var name: String,
    var sDAte: String,
    var dDate: String,
    var memo: String,
    var doneDate: String
)