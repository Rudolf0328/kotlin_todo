package com.myapp.yongwoo.main.done

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yongwoo.R
import com.myapp.yongwoo.add_edit.AddEditActivity
import com.myapp.yongwoo.room.database.MyDatabase
import com.myapp.yongwoo.room.entity.DoneItem
import kotlinx.android.synthetic.main.fragment_done.*

class DoneFragment : Fragment() {
    private var adapter: DoneAdaptor? = null

    var items: MutableList<DoneItem> = mutableListOf()
    private lateinit var myDatabase: MyDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        adapter = DoneAdaptor(view.context)
        done_rcv_item.adapter = adapter
        done_rcv_item.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myDatabase = MyDatabase.getInstance(context!!)!!
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onResume() {
        super.onResume()
        adapter?.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_done_delete, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done_menu_select -> {
                //TODO: listView 만들어서 다중선택 해야지~~~~~

            }

            R.id.done_menu_deleteAll -> {
                myDatabase?.doneDao()?.deleteAllDone()
                adapter?.notifyDataSetChanged()
                adapter?.refresh()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}