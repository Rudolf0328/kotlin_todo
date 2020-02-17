package com.myapp.yongwoo.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DoneItem")
data class DoneItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var name: String,
    var doneDate: String
//    var doneDate: String
) {
    var checked: Boolean = false
}