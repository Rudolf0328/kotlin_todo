package com.myapp.yongwoo.add_edit

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.myapp.yongwoo.R
import com.myapp.yongwoo.room.database.MyDatabase
import com.myapp.yongwoo.room.entity.TodoItem
import kotlinx.android.synthetic.main.activity_add_edit.*
import java.util.*

class AddEditActivity : AppCompatActivity() {

    companion object {
        const val MODE_KEY = "add_edit_mode"
        const val MODE_ADD = 0
        const val MODE_EDIT = 1
    }

    private var mode: Int? = null
    private var database: MyDatabase? = null
    private var id: Int? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        database = MyDatabase.getInstance(this)
        mode = intent.getIntExtra(MODE_KEY, -1)
        actionBar?.run {
            title = when (mode) {
                null -> "Mode error"
                MODE_ADD -> "Add Todo"
                MODE_EDIT -> "Edit Todo"
                else -> {
                    null
                }
            }
            setDisplayHomeAsUpEnabled(true)
        }

        if (mode == MODE_EDIT) {
            id = intent.getIntExtra("todo_id", -1).also {
                database?.todoDao()?.getTodo(it).also { todo ->
                    add_edit_til_todo.editText?.setText(todo?.name)
                    add_edit_til_start_date.editText?.setText(todo?.sDate)
                    add_edit_til_due_date.editText?.setText(todo?.dDate)
                    add_edit_til_memo.editText?.setText(todo?.memo)
                }
            }
        }


        add_edit_til_start_date.editText?.setOnClickListener {
            showCalendar(add_edit_til_start_date)
        }
        add_edit_til_due_date.editText?.setOnClickListener {
            showCalendar(add_edit_til_due_date)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myDatabase = MyDatabase.getInstance(this)

        when (item.itemId) {
            android.R.id.home -> finish()


            R.id.menu_save -> {
                if (!checkIsEmpty(add_edit_til_todo) && !checkIsEmpty(add_edit_til_start_date) && !checkIsEmpty(add_edit_til_due_date)) {
                    if (checkDateOkay(add_edit_til_start_date, add_edit_til_due_date)) {
                        val title = add_edit_til_todo.editText?.text.toString()
                        val sDate = add_edit_til_start_date.editText?.text.toString()
                        val dDate = add_edit_til_due_date.editText?.text.toString()
                        val memo = add_edit_til_memo.editText?.text.toString()
                        when (mode) {
                            null -> {
                                Toast.makeText(this, "mode error", Toast.LENGTH_LONG).show()
                                finish()
                            }
                            MODE_ADD -> {
                                TodoItem(0, title, sDate, dDate, memo).also {
                                    myDatabase?.todoDao()?.insertTodo(it)
                                    finish()
                                }
                            }
                            MODE_EDIT -> {
                                id?.also {
                                    myDatabase?.todoDao()?.getTodo(it)?.also {item ->
                                        item.name = title
                                        item.sDate = sDate
                                        item.dDate = dDate
                                        item.memo = memo
                                        myDatabase.todoDao().updateTodo(item)
                                    }
                                }
                                finish()
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkIsEmpty(til: TextInputLayout): Boolean {
        til.isErrorEnabled = true
        return if (til.editText?.text.toString().trim() == "") {
            til.error = "필수로 채워주세요"
            true
        } else {
            til.error = null
            false
        }
    }

    private fun checkDateOkay(start: TextInputLayout, due: TextInputLayout): Boolean {
        start.isErrorEnabled = true
        due.isErrorEnabled = true

        return if (start.editText?.text.toString() > due.editText?.text.toString()) {
            start.error = "시작 날짜가 더 느려요."
            due.error = "끝나는 날짜가 더 빨라요."
            false
        } else {
            start.error = null
            due.error = null
            true
        }
    }

    private fun showCalendar(til: TextInputLayout) {
        val cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val sMonth: String = if (month + 1 < 10) {
                "0${month + 1}"
            } else {
                (month + 1).toString()
            }
            val sDay: String = if (dayOfMonth < 10) {
                "0${dayOfMonth}"
            } else {
                dayOfMonth.toString()
            }
            til.editText?.setText("$year/$sMonth/$sDay")

        }, mYear, mMonth, mDay).show()
    }
}
