package com.learnproject.aoh1996.avitorecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var elementsList: List<Element>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView = itemView.findViewById<TextView>(R.id.idTextView)
        val deleteImageButton = itemView.findViewById<ImageButton>(R.id.deleteImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (elementsList.isEmpty()) {
            holder.idTextView.text = "No element"
        } else {
            val element = elementsList[position]
            holder.idTextView.text = element.id.toString()
        }
    }

    override fun getItemCount(): Int {
        return elementsList.size
    }
}