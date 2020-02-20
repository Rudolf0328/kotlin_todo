package com.myapp.yongwoo.main.done

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yongwoo.R
import com.myapp.yongwoo.add_edit.AddEditActivity
import kotlinx.android.synthetic.main.fragment_done.*

class DoneFragment : Fragment() {
    private var adapter: DoneAdaptor? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DoneAdaptor(view.context)
        done_rcv_item.adapter = adapter
        done_rcv_item.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onResume() {
        super.onResume()
        adapter?.refresh()
    }
}