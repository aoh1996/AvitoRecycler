package com.learnproject.aoh1996.avitorecycler


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MyViewModel.getInstance()

        var spanCount = 2

        when (this.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                spanCount = 2
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                spanCount = 4
            }

            Configuration.ORIENTATION_UNDEFINED -> {
                spanCount = 2
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val myAdapter = MyAdapter()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)

        viewModel.elementsLiveData.observe(this, { elements ->
            myAdapter.submitList(
                elements,
                (viewModel.editPositionLiveData.value ?: 0),
                viewModel.operation
            )
        })
        recyclerView.adapter = myAdapter

    }
}