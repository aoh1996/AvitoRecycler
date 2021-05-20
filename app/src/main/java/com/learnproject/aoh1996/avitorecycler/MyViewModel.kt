package com.learnproject.aoh1996.avitorecycler

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.ArrayList

private const val TAG = "MyViewModel"

class MyViewModel : ViewModel() {

    var elements = Collections.synchronizedList(ArrayList<Element>())
    var deletedElements = Collections.synchronizedList(ArrayList<Element>())

    val mutex = ReentrantLock()

    private val _elementsLivaData = MutableLiveData<List<Element>>()
    val elementsLiveData: LiveData<List<Element>> = _elementsLivaData

    private val _insertPositionLiveData = MutableLiveData<Int>()
    val insertPositionLiveData: LiveData<Int> = _insertPositionLiveData


    init {
        Log.d(TAG, "init block called:")
        loadMockupElements()
        for (e in elements) {
            Log.d(TAG, "Element: $e")
        }
        startWorker()
    }

    private fun loadMockupElements() {
        Log.d(TAG, "loadMockupElements: start")
        val mockupList = ArrayList<Element>()
        for (i in 0 until 15) {
            mockupList.add(Element())
        }
        elements = mockupList
        _elementsLivaData.value = elements
        Log.d(TAG, "loadMockupElements: end")
    }

    private fun startWorker() {
        Log.d(TAG, "startWorker: launched")

        viewModelScope.launch {
            while (true) {
                delay(5000)
                try {
                    mutex.lock()
                    val insertPosition = (0..elements.size).random()
                    _insertPositionLiveData.postValue(insertPosition)
                    if (deletedElements.isEmpty()) {
                        val newElement = Element()
                        elements.add(insertPosition, newElement)
                        _elementsLivaData.postValue(elements)
                    } else {
                        val newElement = deletedElements[(0..(deletedElements.size - 1)).random()]
                        elements.add(insertPosition, newElement)
                        _elementsLivaData.postValue(elements)
                        deletedElements.remove(newElement)
                    }
                } finally {
                    mutex.unlock()
                }

//
                Log.d(TAG, "worker: ${elements.size}")
            }
        }

//        Thread {
//            while (true) {
//                sleep(5000)
//                try {
//                    mutex.lock()
//                    val insertPosition = (0..elements.size).random()
//                    _insertPositionLiveData.postValue(insertPosition)
//                    if (deletedElements.isEmpty()) {
//                        val newElement = Element()
//                        elements.add(insertPosition, newElement)
////                        _newElementLiveData.postValue(newElement)
//                        _elementsLivaData.postValue(elements)
//                    } else {
//                        val newElement = deletedElements[(0..(deletedElements.size - 1)).random()]
//                        elements.add(insertPosition, newElement)
//                        _elementsLivaData.postValue(elements)
////                        _newElementLiveData.postValue(newElement)
//                        deletedElements.remove(newElement)
//                    }
//                } finally {
//                    mutex.unlock()
//                }
//
////
//                Log.d(TAG, "worker: ${elements.size}")
//            }
//        }.start()

    }

    fun deleteElement(e: Element) {
        deletedElements.add(e)
        elements.remove(e)
    }

    companion object {
        private lateinit var instance: MyViewModel

        @MainThread
        fun getInstance(): MyViewModel {
            instance = if (::instance.isInitialized) instance else MyViewModel()
            return instance
        }
    }
}