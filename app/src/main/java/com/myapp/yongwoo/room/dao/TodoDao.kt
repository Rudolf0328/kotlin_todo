package com.myapp.yongwoo.room.dao

import androidx.room.*
import com.myapp.yongwoo.room.entity.TodoItem

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(item: TodoItem)

    @Delete
    fun deleteTodo(item: TodoItem)

    @Update
    fun updateTodo(item: TodoItem)

    @Query("SELECT * from todo ORDER BY dDate")
    fun getAllTodo(): List<TodoItem>

    @Query("SELECT * from todo WHERE id = :id")
    fun getTodo(id: Int): TodoItem
}