package com.datastructure.kotlin.queue

import java.util.*

class Queue<T> {
    var linkedList: LinkedList<T> = LinkedList()

    constructor() {}

    constructor(firstElement: T) {
        enqueue(firstElement)
    }

    public fun size(): Int = linkedList.size
    public fun isEmpty(): Boolean = size() == 0
    public fun enqueue(element: T) {
        linkedList.addLast(element)
    }

    public fun dequeue(): T {
        if (isEmpty()) throw EmptyStackException()
        return linkedList.removeFirst()
    }

    fun peek(): T? {
        if (isEmpty()) throw EmptyStackException()
        return linkedList.peekFirst()
    }


    fun clear() {
        if (!isEmpty())
            linkedList.clear()
    }
}