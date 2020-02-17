package com.myapp.yongwoo.main.done

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yongwoo.room.entity.DoneItem
import kotlinx.android.synthetic.main.item_done.view.*


class DoneVeiwHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(item: DoneItem) {
        //이미 체크 된건데 체크드가 필요할까?
        itemView.done_cb.isChecked = item.checked
        itemView.done_tv_name.text = item.name
    }
}