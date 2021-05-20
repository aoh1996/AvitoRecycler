package com.learnproject.aoh1996.avitorecycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MyAdapter"

class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val viewModel = MyViewModel.getInstance()
    val mDiffer = AsyncListDiffer(this, DiffCallback)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView = itemView.findViewById<TextView>(R.id.idTextView)
        val deleteImageButton = itemView.findViewById<ImageButton>(R.id.deleteImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mDiffer.currentList.isEmpty()) {
            holder.idTextView.text = "No element"
        } else {

            holder.idTextView.text = position.toString()
            holder.deleteImageButton.setOnClickListener {

                try {
                    viewModel.mutex.lock()
                    val currentPosition = holder.adapterPosition
                    val elementToRemove = mDiffer.currentList[currentPosition]
                    viewModel.deleteElement(elementToRemove, currentPosition)
//                    Log.d(TAG, "Removed element position: $currentPosition" +
//                            "\nRemoved element id: $elementToRemove")
                } finally {
                    viewModel.mutex.unlock()
                }


            }
        }
    }

    fun submitList(list: List<Element>, position: Int, operation: Operation){

        Log.d(TAG, "submitList() called with operation $operation on position $position ")

        when(operation) {
            Operation.ADD -> {
                mDiffer.submitList(list)
                notifyItemInserted(position)
                notifyItemRangeChanged(position, mDiffer.currentList.size)
            }
            Operation.REMOVE -> {
                notifyItemRemoved(position)
                Log.d(TAG, "notifyItemRemoved() called on position $position")
                notifyItemRangeChanged(position, mDiffer.currentList.size)
                Log.d(TAG, "notifyItemRangeChanged() $position - ${mDiffer.currentList.size} ")
                mDiffer.submitList(list)

            }
            Operation.NO -> {}
        }

    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Element>() {
        override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
            return oldItem.equals(newItem)
        }

    }
}