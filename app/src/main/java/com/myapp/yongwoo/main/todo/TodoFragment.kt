package com.myapp.yongwoo.main.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yongwoo.R
import com.myapp.yongwoo.add_edit.AddEditActivity
import com.myapp.yongwoo.room.database.MyDatabase
import com.myapp.yongwoo.room.entity.DoneItem
import com.myapp.yongwoo.room.entity.TodoItem
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.item_todo.*
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : Fragment() {

    private var adapter: TodoAdaptor? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        adapter = TodoAdaptor(view.context)
        main_rcv_item.adapter = adapter
        main_rcv_item.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        main_fab_add.setOnClickListener{
            val intent = Intent(view.context, AddEditActivity::class.java).apply {
                putExtra(AddEditActivity.MODE_KEY, AddEditActivity.MODE_ADD)
            }
            startActivity(intent)
        }
   }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onResume() {
        super.onResume()
        adapter?.refresh()
    }

    //Todo: checked 한개라도 있으면 done button 만들
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_done, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myDatabase = MyDatabase.getInstance(context!!)    //this하면 빨간색 줄이에요

        when (item.itemId) {

            R.id.menu_done -> {
                val myDatabase = MyDatabase.getInstance(context!!)
                myDatabase?.todoDao()?.getTodoDone()?.let {
                    for(item in it) {
                        myDatabase.doneDao().insertDone(changeDoneItem(item))
                        myDatabase.todoDao().deleteTodo(item)
                    }
                    adapter?.refresh()
                }

            }

        }
        return super.onOptionsItemSelected(item)
    }
    private fun changeDoneItem(item: TodoItem): DoneItem {
        val today = SimpleDateFormat("yyyy/MM/dd").format(Date())
        return DoneItem(0, item.name, item.sDate, item.dDate, item.memo, today)
    }
}
