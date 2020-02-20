package com.myapp.yongwoo.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapp.yongwoo.room.dao.DoneDao
import com.myapp.yongwoo.room.dao.TodoDao
import com.myapp.yongwoo.room.entity.DoneItem
import com.myapp.yongwoo.room.entity.TodoItem

@Database(entities = [TodoItem::class, DoneItem::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao
    abstract fun doneDao() : DoneDao

    companion object {
        private var myDatabase: MyDatabase? = null

        fun getInstance(context: Context):MyDatabase? {
            if(myDatabase == null) {
                //다른 비동기처리할 때 필요
                //비동기처리 -> main thread에서 에러가 나지 않도록 main thread 먼저 보여주고 다른 thread에서 작업.
                //synchronized(MyDatabase::class) {
                    myDatabase = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "MyDatabase.db")
                    .allowMainThreadQueries().build()
                //}

            }

            return myDatabase
        }
    }
}

