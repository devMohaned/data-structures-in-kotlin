package com.datastructure.kotlin

import java.lang.NullPointerException


class ArrayList<T> {
    private var length: Int = 0
    private var capacity: Int = 16
    private var arr: Array<T?>

    constructor() : this(16) {}

    @Suppress("UNCHECKED_CAST") // Annoying Cast
    constructor(cap: Int) {
        if (cap < 0) throw IllegalArgumentException("Illegal Argument of Negative Capacity")
        capacity = cap
        arr = arrayOfNulls<Any>(cap) as Array<T?>
    }

    fun size(): Int {
        return length
    }

    fun isEmpty(): Boolean {
        return size() == 0
    }

    fun get(index: Int): T? {
        if (index in 0..length)
            return arr[index]
        else throw IndexOutOfBoundsException("Index is not within Bounderies")
    }

    fun set(index: Int, element: T) {
        if (index in 0..capacity) {
            arr[index] = element
        }
    }

    fun clear() {
        for (i in 0 until capacity - 1) {
            arr[i] = null
        }
        length = 0
    }

    @Suppress("UNCHECKED_CAST")
    fun add(element: T) {
        if (length + 1 > capacity) {
            if (capacity == 0) capacity = 1
            else capacity *= 2
            val newArray: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>
            for (i in 0..capacity) {
                newArray[i] = arr[i]
            }
            arr = newArray
        }
        arr[length++] = element
    }

    @Suppress("UNCHECKED_CAST")
    fun removeAt(index: Int): T? {
        if (index < 0 || index !in 0..capacity) throw IndexOutOfBoundsException("Index is not within Bounderies")

        val item: T? = arr[index]
        val newArray: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>
        var newArrayIndexReference = 0
        for (elementIndex in arr.indices) {
            if (elementIndex == index) {
                // Skip it if the element is found
                newArrayIndexReference--
            } else {
                newArray[newArrayIndexReference] = arr[elementIndex]
            }
            newArrayIndexReference++
        }

        arr = newArray
        length -= 1
        return item
    }

    fun remove(element: T?): Boolean {
        if (element == null)
            throw NullPointerException("Null Element cannot be removed")
        for (i in 0..length) {
            if (element == get(i)) {
                removeAt(i)
                return true
            }

        }
        return false
    }

    fun indexOf(element: T?): Int {
        if (element == null)
            throw NullPointerException("Null Element cannot be removed")
        for (i in 0..length) {
            if (element == get(i)) {
                return i
            }

        }
        return -1
    }

    fun contains(element: T?): Boolean {
        return indexOf(element) != -1

    }

}