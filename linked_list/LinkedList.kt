package com.datastructure.kotlin.linked_list

abstract class LinkedList<T> {
    abstract fun add(obj: T)
    abstract fun addFirst(obj: T)
    abstract fun addLast(obj: T)
    abstract fun remove(obj: T): Boolean
    abstract fun removeFirst() : T?
    abstract fun removeLast(): T?
    abstract fun contains(obj: T) : Boolean
    abstract fun peekFirst() : T?
    abstract fun peekLast(): T?
    abstract fun clear()
    abstract fun indexOf(obj: T): Int
}