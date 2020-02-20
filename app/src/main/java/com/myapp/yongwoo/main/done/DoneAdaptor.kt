package com.myapp.yongwoo.main.done

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
import com.myapp.yongwoo.room.entity.DoneItem
import java.text.SimpleDateFormat
import java.util.*

class DoneAdaptor(private val context: Context) : RecyclerView.Adapter<DoneViewHolder>() {


    private var items: MutableList<DoneItem> = mutableListOf()
    private val myDatabase = MyDatabase.getInstance(context)
    //생성자가 만들어진다.
    init {
         myDatabase?.doneDao()?.getAllDone()?.also {
            items.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun deleteItem(item: DoneItem) {
        items.remove(item)
        myDatabase?.doneDao()?.deleteDone(item)
        notifyDataSetChanged()
    }

    fun addItem(item: DoneItem) {
        items.add(item)
        myDatabase?.doneDao()?.insertDone(item)
        notifyDataSetChanged()
    }

    fun refresh() {
        myDatabase?.doneDao()?.getAllDone()?.also {
            items.clear()
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneViewHolder {
        val viewHolder: DoneViewHolder =
            DoneViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_done,
                    parent,
                    false
                )
            )

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
                            it.putExtra("done_id", items[viewHolder.adapterPosition].id)
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

    override fun onBindViewHolder(holder: DoneViewHolder, position: Int) {
        holder.onBind(items[position])
    }

}

