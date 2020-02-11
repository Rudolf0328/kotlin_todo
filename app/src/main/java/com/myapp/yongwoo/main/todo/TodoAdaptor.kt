package com.myapp.yongwoo.main.todo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yongwoo.R
import com.myapp.yongwoo.add_edit.AddEditActivity
import com.myapp.yongwoo.room.database.MyDatabase
import com.myapp.yongwoo.room.entity.TodoItem

class TodoAdaptor(private val context: Context) : RecyclerView.Adapter<TodoVeiwHolder>() {
    private var items: MutableList<TodoItem> = mutableListOf()
    private val myDatabase = MyDatabase.getInstance(context)
    //생성자가 만들어진다.
    init {
        val itemList = myDatabase?.todoDao()?.getAllTodo()?.also {
            items.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun deleteItem(item: TodoItem) {
        items.remove(item)
        myDatabase?.todoDao()?.deleteTodo(item)
        notifyDataSetChanged()
    }

    fun addItem(item: TodoItem) {
        items.add(item)
        myDatabase?.todoDao()?.insertTodo(item)
        notifyDataSetChanged()
    }

    fun refresh() {
        myDatabase?.todoDao()?.getAllTodo()?.also {
            items.clear()
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVeiwHolder {
        val viewHolder: TodoVeiwHolder =
            TodoVeiwHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_todo,
                    parent,
                    false
                )
            )

        viewHolder.itemView.setOnClickListener{
            items[viewHolder.adapterPosition].checked = !items[viewHolder.adapterPosition].checked
            myDatabase?.todoDao()?.updateTodo(items[viewHolder.adapterPosition])
            notifyDataSetChanged()
        }

        viewHolder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(parent.context)
            val menu: Array<String> = arrayOf("삭제", "수정", "취소")
            builder.setTitle(items[viewHolder.adapterPosition].name)
            builder.setItems(menu) { dialog, which ->
                when(menu[which]) {

                    "삭제" -> deleteItem(items[viewHolder.adapterPosition])
                    "수정" -> {
                        Intent(context, AddEditActivity::class.java).let {
                            it.putExtra(AddEditActivity.MODE_KEY, AddEditActivity.MODE_EDIT)
                            it.putExtra("todo_id", items[viewHolder.adapterPosition].id)
                            context.startActivity(it)
                        }
                    }
                    "취소" -> {
                        //nothing
                    }
                    else -> {
                        Log.d("SetOnLongClickListener", "item position error!")
                    }
                }
            }.show()

            false

        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoVeiwHolder, position: Int) {
        holder.onBind(items[position])
    }

}