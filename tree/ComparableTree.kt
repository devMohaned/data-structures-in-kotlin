package com.datastructure.kotlin.tree

abstract class ComparableTree<T: Comparable<T>> : Tree<T>() {

    abstract fun insert(value:  T): Tree<T>
    abstract fun contains(value: T): Boolean
    abstract fun find(value: T): Node<T>?
}