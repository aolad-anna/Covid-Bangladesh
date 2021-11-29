package com.example.covidbangladesh.models

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidbangladesh.R
import com.google.android.gms.common.util.CollectionUtils.listOf

class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    var NotifyList : List<Notify> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recy,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return NotifyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.Rdate.text = NotifyList.get(position).ndate
        holder.Rinfo.text = NotifyList.get(position).details
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNotifyListItems(NotifyList: List<Notify>){
        this.NotifyList = NotifyList;
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val Rdate: TextView = itemView!!.findViewById(R.id.date)
        val Rinfo: TextView = itemView!!.findViewById(R.id.info)

    }
}