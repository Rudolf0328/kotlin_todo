package com.myapp.yongwoo.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodoItem")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var sDate: String,
    var dDate: String,
    var memo: String
) {
    var checked: Boolean = false
}