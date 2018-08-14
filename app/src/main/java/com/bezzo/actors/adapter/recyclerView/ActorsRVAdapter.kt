package com.bezzo.actors.adapter.recyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bezzo.actors.R
import com.bezzo.actors.base.BaseHolder
import com.bezzo.actors.data.model.Actors
import com.bezzo.actors.listener.OnItemClickListener
import com.bezzo.actors.util.GlideApp
import kotlinx.android.synthetic.main.item_rv_actor.view.*

/**
 * Created by bezzo on 11/01/18.
 * Change String to model you need convert to recycler view
 */
class ActorsRVAdapter(var context : Context,
                      var list : ArrayList<Actors>)
    : RecyclerView.Adapter<ActorsRVAdapter.Item>() {

    lateinit var listener : OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Item, position: Int) {
        holder.model = list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item {
        return Item(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_actor, parent, false))
    }


    inner class Item(itemView : View) : BaseHolder<Actors>(itemView){

        init {
            itemView.setOnClickListener {
                listener.onItemClick(it, layoutPosition)
            }

            itemView.setOnLongClickListener {
                listener.onItemLongClick(it, layoutPosition)
            }
        }

        override fun setContent(model: Actors) {
            GlideApp.with(context).load(model.image)
                    .circleCrop()
                    .into(itemView.iv_image)
            itemView.tv_name.text = model.name
            itemView.tv_dob.text = model.dob
        }
    }
}