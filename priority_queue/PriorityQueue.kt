package com.datastructure.kotlin.priority_queue

data class Node<T>(val element: T, val priority: Int){
    override fun toString(): String {
        return "${element.toString()} ($priority)"
    }
}

class PriorityQueue<T> {
    val values: MutableList<Node<T>> = mutableListOf()

    public fun enqueue(element: T, priority: Int) {
        val node: Node<T> = Node(element, priority)
        values.add(node)
        bubbleUp()

    }

    // Parent Formula -> (Index - 1)/2 (Ignore Decimals)
    // Left Child Formula -> (Index*2) + 1
    // Right Child Formula -> (Index*2) + 2
    private fun bubbleUp() {
        var elementIndex = values.size - 1
        val element = values[elementIndex]
        var parentIndex: Int = -1
        var parent: Node<T>
        while (elementIndex > 0) {
            parentIndex = (elementIndex - 1) / 2
            parent = values[parentIndex]
            if (element.priority >= parent.priority) // TODO: To Reverse to Max Binary Heap Change Operator to <=
                break

            values[parentIndex] = element
            values[elementIndex] = parent
            elementIndex = parentIndex

        }
    }

    public fun dequeue(): Node<T>? {
        if (values.isEmpty())
            return null

        val min: Node<T> = values[0]
        val lastElement: Node<T> = values.removeLast()
        if (values.size > 0) {
            values[0] = lastElement
            bubbleDown()
        }
        return min
    }

    private fun bubbleDown() {
        var elementIndex: Int = 0
        val N: Int = values.size
        var element: Node<T> = values[elementIndex]
        while (true) {
            val leftChildIndex: Int = (elementIndex * 2) + 1
            val rightChildIndex: Int = (elementIndex * 2) + 2

            var leftChild:  Node<T>? = null
            var rightChild:  Node<T>? = null
            var swap: Int? = null

            if (leftChildIndex < N) {
                leftChild = values[leftChildIndex]
                if (leftChild.priority < element.priority) { // TODO: To Reverse to Max Binary Heap Change Operator to >
                    swap = leftChildIndex
                }
            }
            if (rightChildIndex < N) {
                rightChild = values[rightChildIndex]
                if ((swap == null && rightChild.priority < element.priority)// TODO: To Reverse to Max Binary Heap Change Operator to >
                    || (swap != null && rightChild.priority < leftChild!!.priority)) {// TODO: To Reverse to Max Binary Heap Change Operator to >
                    swap = rightChildIndex
                }
            }
            if (swap == null)
                break

            values[elementIndex] = values[swap]
            values[swap] = element
            elementIndex = swap

        }
    }

    override fun toString(): String {
        return values.toString()
    }
}