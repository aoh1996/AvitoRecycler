package com.learnproject.aoh1996.avitorecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel by viewModels<MyViewModel>()
        val list = viewModel.elements.value

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val myAdapter = list?.let { MyAdapter(it) }
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.setStackFromEnd(true)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = myAdapter


        viewModel.elements.observe(this, { elements ->
            myAdapter?.notifyItemInserted(elements.size)
            recyclerView.scrollToPosition(elements.size - 1)
        })


    }

}