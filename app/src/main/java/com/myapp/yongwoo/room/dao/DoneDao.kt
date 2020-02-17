package com.myapp.yongwoo.room.dao

import androidx.room.*
import com.myapp.yongwoo.room.entity.DoneItem

@Dao
interface DoneDao {
    @Insert
    fun insertDone(item: DoneItem)

    @Delete
    fun deleteDone(item: DoneItem)

    @Update
    fun updateDone(item: DoneItem)

    @Query("SELECT * from DoneItem ORDER BY dDate")
    fun getAllDone(): List<DoneItem>

    @Query("SELECT * from DoneItem WHERE id = :id")
    fun getDone(id: Int): DoneItem
}