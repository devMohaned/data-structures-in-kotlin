package com.datastructure.kotlin.tree

import com.datastructure.kotlin.queue.Queue


public class DepthFirstSearch() {
    private val tree: BinarySearchTree<Int> = BinarySearchTree()

    init {
        tree.insert(10)
        tree.insert(6)
        tree.insert(15)
        tree.insert(20)
        tree.insert(3)
        tree.insert(8)
    }

    public fun dpsPreOrder(): MutableList<Int?> {
        val data = mutableListOf<Int?>()
        traversePreOrder(data,tree.root)
        return data
    }

    private fun traversePreOrder(
        data: MutableList<Int?> = mutableListOf<Int?>(), it: Node<Int>?
    ) {
        updateData(data, it)
        if (it?.left != null) traversePreOrder(data, it.left)
        if (it?.right != null) traversePreOrder(data, it.right)
    }

    public fun dpsPostOrder(): MutableList<Int?> {
        val data = mutableListOf<Int?>()
        traversePostOrder(data,tree.root)
        return data
    }

    private fun traversePostOrder(
        data: MutableList<Int?> = mutableListOf<Int?>(), it: Node<Int>?
    ) {
        if (it?.left != null) traversePostOrder(data, it.left)
        if (it?.right != null) traversePostOrder(data, it.right)
        updateData(data, it)
    }


    public fun dpsInOrder(): MutableList<Int?> {
        val data = mutableListOf<Int?>()
        traverseInOrder(data,tree.root)
        return data
    }

    private fun traverseInOrder(
        data: MutableList<Int?> = mutableListOf<Int?>(), it: Node<Int>?
    ) {
        if (it?.left != null) traverseInOrder(data, it.left)
        updateData(data, it)
        if (it?.right != null) traverseInOrder(data, it.right)
    }



    private fun updateData(data: MutableList<Int?> = mutableListOf<Int?>(),it: Node<Int>?){
        data.add(it?.value)
    }


}

class BreadthFirstSearch {

    private val tree: BinarySearchTree<Int> = BinarySearchTree()

    init {
        tree.insert(10)
        tree.insert(6)
        tree.insert(15)
        tree.insert(20)
        tree.insert(3)
        tree.insert(8)
    }

    public fun breadthFirstSearch(): MutableList<Int?> {
        var node: Node<Int>? = tree.root
        val data = mutableListOf<Int?>()
        val queue: Queue<Node<Int>?>  = Queue(node);
        queue.enqueue(node)

        while(!queue.isEmpty())
        {
            node = queue.dequeue();
            data.add(node?.value)
            if(node?.left != null)queue.enqueue(node.left);
            if(node?.right != null) queue.enqueue(node.right)
        }
        return data
    }


}