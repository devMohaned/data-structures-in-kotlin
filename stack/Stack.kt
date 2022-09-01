package com.datastructure.kotlin.stack

import java.util.*


// Using Java's LinkedList (Works fine with my single linked list - feel free to change the type)
class Stack<T> {
    var linkedList: LinkedList<T> = LinkedList()

    constructor() {}

    constructor(firstElement: T) {
        push(firstElement)
    }

    public fun size(): Int = linkedList.size
    public fun isEmpty(): Boolean = size() == 0
    public fun push(element: T) {
        linkedList.addLast(element)
    }

    public fun pop(): T {
        if (isEmpty()) throw EmptyStackException()
        return linkedList.removeLast()
    }

    fun peek(): T? {
        if (isEmpty()) throw EmptyStackException()
        return linkedList.peekLast()
    }


    fun clear() {
        if (!isEmpty())
            linkedList.clear()
    }


}