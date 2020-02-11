package com.myapp.yongwoo.main.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yongwoo.R
import com.myapp.yongwoo.add_edit.AddEditActivity
import kotlinx.android.synthetic.main.fragment_todo.*

class TodoFragment : Fragment() {
    private var adapter: TodoAdaptor? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
