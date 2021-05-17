package com.learnproject.aoh1996.avitorecycler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MyViewModel"

class MyViewModel : ViewModel() {

    private var synchronizedElements = Collections.synchronizedList( ArrayList<Element>() )

    private val _elements = MutableLiveData<List<Element>>()
    val elements: LiveData<List<Element>> = _elements

    init {
        Log.d(TAG, "init block called:")
        loadMockupElements()
        for (e in synchronizedElements) {
            Log.d(TAG, "Element: ${e.id}")
        }
        startWorker()
    }

    private fun loadMockupElements() {

            Log.d(TAG, "loadMockupElements: start")
            val mockupList = ArrayList<Element>()
            for (i in 0 until 15) {
                mockupList.add(Element(i))
            }
            _elements.value = mockupList
            synchronizedElements = mockupList
            _elements.postValue(synchronizedElements)
            Log.d(TAG, "loadMockupElements: end")
    }

    private fun startWorker() {
        Log.d(TAG, "startWorker: launched")

        Thread {
            while (true) {
                sleep(5000)
                    val size = synchronizedElements.size
                    val newElement = Element(size)
                    synchronizedElements.add(newElement)
                    _elements.postValue(synchronizedElements)
                    Log.d(TAG, "Element: ${newElement.id}")
            }
        }.start()

    }
}