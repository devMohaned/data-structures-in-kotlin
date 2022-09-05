package com.datastructure.kotlin.heap

class MaxBinaryHeap<T : Comparable<T>> {
    val values: MutableList<T> = mutableListOf<T>()

    public fun insert(element: T) {
        values.add(element)
        bubbleUp()

    }

    // Parent Formula -> (Index - 1)/2 (Ignore Decimals)
    // Left Child Formula -> (Index*2) + 1
    // Right Child Formula -> (Index*2) + 2
    private fun bubbleUp() {
        var elementIndex = values.size - 1
        val element = values[elementIndex]
        var parentIndex: Int = -1
        var parent: T
        while (elementIndex > 0) {
            parentIndex = (elementIndex - 1) / 2
            parent = values[parentIndex]
            if (element <= parent)
                break

            values[parentIndex] = element
            values[elementIndex] = parent
            elementIndex = parentIndex

        }
    }

    public fun removeMax(): T? {
        if (values.isEmpty())
            return null

        val max: T = values[0]
        val lastElement: T = values.removeLast()
        if (values.size > 0) {
            values[0] = lastElement
            bubbleDown()
        }
        return max
    }

    private fun bubbleDown() {
        var elementIndex: Int = 0
        val N: Int = values.size
        var element: T = values[elementIndex]
        while (true) {
            val leftChildIndex: Int = (elementIndex * 2) + 1
            val rightChildIndex: Int = (elementIndex * 2) + 2

            var leftChild: T? = null
            var rightChild: T? = null
            var swap: Int? = null

            if (leftChildIndex < N) {
                leftChild = values[leftChildIndex]
                if (leftChild > element) {
                    swap = leftChildIndex
                }
            }
            if (rightChildIndex < N) {
                rightChild = values[rightChildIndex]
                if ((swap == null && rightChild > element) || (swap != null && rightChild > leftChild!!)) {
                    swap = rightChildIndex
                }
            }
            if (swap == null)
                break

            values[elementIndex] = values[swap!!]
            values[swap!!] = element
            elementIndex = swap!!

        }
    }

    override fun toString(): String {
        return values.toString()
    }
}