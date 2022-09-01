package com.datastructure.kotlin.tree


class Node<T>(var value: T?, var left: Node<T>?, var right: Node<T>?)

class BinarySearchTree<T : Comparable<T>> : ComparableTree<T>() {
    private var root: Node<T>? = null

    override fun insert(value: T): BinarySearchTree<T> {
        val newNode = Node(value, null, null)
        if (root == null) {
            root = newNode
            return this
        } else {
            var current: Node<T> = root!!
            while (true) {
                if (value < current.value!!) {
                    if (current.left == null) {
                        current.left = newNode
                        return this
                    }
                    // Left is not null
                    current = current.left!!
                } else if (value > current.value!!) {
                    if (current.right == null) {
                        current.right = newNode
                        return this
                    }
                    // Right is not null
                    current = current.right!!
                } else {
                    // Value == Current
                    // Just ignore it
                    return this
                }
            }
        }


    }

    override fun contains(value: T): Boolean = find(value) != null


    override fun find(value: T): Node<T>? {
        if (root == null) return null
        //              46
        //          30       73
        //      15    40   60   80
        else {
            var current: Node<T>? = root!!
            while (true) {
                if (current == null) // Leaf (No Result Found)
                    return current
                else if (value < current.value!!) {
                    current = current.left!!
                } else if (value > current.value!!) {
                    current = current.right
                } else {
                    // Value = current Value
                    return current
                }
            }
        }

    }


    /*  Trying to implement it without recursion

    override fun remove(value: T): Node<T>? {
        if (root == null) return null
        //              46
        //          30       73
        //      15    40   60   80
        else {
            var current: Node<T>? = root!!
            while (true) {
                if (current == null) // Leaf (No Result Found)
                    return current
                else if (value < current.value!!) {
                    current = current.left!!
                } else if (value > current.value!!) {
                    current = current.right
                } else {
                    // Value = current Value (Delete)
                    if (current.left == null && current.right == null) {
                        // This is a leaf, delete it safely
                        current = null
                    } else if (
                        (current.left == null && current.right != null)
                        || (current.left != null && current.right == null)) {
                        // There's only 1 child
                        var child:Node<T>? = if(current.left != null)  current.left else current.right
                        val temp = child
                        current = temp
                        child = null
                    }else{
                        // There are 2 children

                    }
                    return current
                }
            }
        }

    }*/

    // Converted this method's code from: https://github.com/williamfiset/DEPRECATED-data-structures/blob/master/com/williamfiset/datastructures/binarysearchtree/BinarySearchTree.java
    // Remove a value from this binary tree if it exists
    fun remove(elem: T): Boolean {
        if (contains(elem)) {
            root = remove(root, elem)
            return true
        }
        return false
    }

    private fun remove(node: Node<T>?, elem: T): Node<T>? {
        var node: Node<T>? = node ?: return null
        val cmp = elem.compareTo(node!!.value!!)

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value
        if (cmp < 0) {
            node.left = remove(node.left, elem)

            // Dig into right subtree, the value we're looking
            // for is greater than the current value
        } else if (cmp > 0) {
            node.right = remove(node.right, elem)

            // Found the node we wish to remove
        } else {

            // This is the case with only a right subtree or
            // no subtree at all. In this situation just
            // swap the node we wish to remove with its right child.
            if (node.left == null) {
                val rightChild = node.right
                node.value = null
                node = null
                return rightChild

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.right == null) {
                val leftChild = node.left
                node.value = null
                node = null
                return leftChild

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. In this implementation I have decided to find the
                // smallest value in the right subtree which can be found by
                // traversing as far left as possible in the right subtree.
            } else {

                // Find the leftmost node in the right subtree
                val tmp: Node<T> = findMin(node.right!!)

                // Swap the data
                node.value = tmp.value

                // Go into the right subtree and remove the leftmost node we
                // found and swapped data with. This prevents us from having
                // two nodes in our tree with the same value.
                node.right = remove(node.right, tmp.value!!)

                // If instead we wanted to find the largest node in the left
                // subtree as opposed to smallest node in the right subtree
                // here is what we would do:
                // Node tmp = findMax(node.left);
                // node.data = tmp.data;
                // node.left = remove(node.left, tmp.data);
            }
        }
        return node
    }

    // Helper method to find the leftmost node (which has the smallest value)
    private fun findMin(node: Node<T>): Node<T> {
        var node: Node<T>? = node
        while (node!!.left != null) node = node.left
        return node
    }

    // Helper method to find the rightmost node (which has the largest value)
    private fun findMax(node: Node<T>): Node<T>? {
        var node: Node<T>? = node
        while (node!!.right != null) node = node.right
        return node
    }
}