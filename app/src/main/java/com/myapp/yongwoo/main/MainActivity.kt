package com.myapp.yongwoo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myapp.yongwoo.R
import com.myapp.yongwoo.main.done.DoneFragment
import com.myapp.yongwoo.main.todo.TodoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
                    supportFragmentManager.beginTransaction().replace(R.id.main_framelayout, fragment2).commitAllowingStateLoss()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }
}
