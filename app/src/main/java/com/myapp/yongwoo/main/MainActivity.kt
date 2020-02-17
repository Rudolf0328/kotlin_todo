package com.myapp.yongwoo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.myapp.yongwoo.R
import com.myapp.yongwoo.main.done.DoneFragment
import com.myapp.yongwoo.main.todo.TodoFragment
import com.myapp.yongwoo.room.database.MyDatabase
import com.myapp.yongwoo.room.entity.DoneItem
import com.myapp.yongwoo.room.entity.TodoItem
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_done.*
import kotlinx.android.synthetic.main.item_todo.*

class MainActivity : AppCompatActivity() {

//    private var items: MutableList<TodoItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment1 = TodoFragment()
        val fragment2 = DoneFragment()

        supportActionBar?.title = "Todo List"
        supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, fragment1).commitAllowingStateLoss()

        main_bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_main_todo -> {
                    supportActionBar?.title = "Todo List"
                    supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, fragment1)
                        .commitAllowingStateLoss()
                    return@setOnNavigationItemSelectedListener true

                }

                R.id.navigation_main_done -> {
                    supportActionBar?.title = "Done List"
                    supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, fragment2)
                        .commitAllowingStateLoss()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }


}
