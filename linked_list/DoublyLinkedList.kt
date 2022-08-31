package com.datastructure.kotlin.linked_list


data class Node<T>(var data: T, var prev: Node<T>?, var next: Node<T>?)

class DoublyLinkedList<T> : LinkedList<T>() {
    private var size = 0
    private var head: Node<T?>? = null
    private var tail: Node<T?>? = null

    override fun add(obj: T) {
        addLast(obj)
    }

    override fun addFirst(obj: T) {
        if (isEmpty()) {
            val node = Node<T?>(obj, null, null)
            head = node
            tail = node
        } else {
            head?.prev = Node<T?>(obj, null, head)
            head = head?.prev
        }
        size++
    }

    override fun addLast(obj: T) {
        if (isEmpty()) {
            val node = Node<T?>(obj, null, null)
            head = node
            tail = node
        } else {
            tail?.next = Node<T?>(obj, tail, null)
            tail = tail?.next
        }

        size++
    }

    fun remove(obj: Node<T?>?): T? {
        if(obj?.prev == null) return removeFirst()
        if(obj.next == null) return removeLast()

        obj.prev?.next = obj.next
        obj.next?.prev = obj.prev

        val data: T? = obj.data
        obj.data = null
        obj.apply {
            next = null
            prev = null
        }
        --size

        return data

    }

    override fun remove(obj: T): Boolean {
        var trav: Node<T?>? = head

        if(obj == null)
        {
            while (trav != null)
            {
                if(trav.data == null)
                {
                    remove(trav)
                    return true
                }
                trav = trav.next
            }
        }else {
            while (trav != null) {
                if (obj == trav.data) {
                    remove(trav)
                    return true
                }
                trav = trav.next
            }
        }
        return false
    }

    override fun removeFirst(): T? {
        if (isEmpty()) throw RuntimeException("Empty list")

        val data: T? = head?.data
        head = head?.next
        --size

        if (isEmpty()) tail = null
        else head?.prev = null

        return data
    }

    override fun removeLast(): T? {
        if (isEmpty()) throw RuntimeException("Empty list")

        val data: T? = tail?.data
        tail = tail?.prev
        --size

        if (isEmpty()) head = null
        else tail?.next = null

        return data
    }

    override fun contains(obj: T): Boolean {
        return indexOf(obj) != -1;
    }

    override fun clear() {
        var trav: Node<T?>? = head
        while (trav != null) {
            val next = trav.next
            trav.apply {
                this.next = null
                prev = null
                data = null
            }
            trav = next
        }
        head = null
        tail = null
        // trav = null
        size = 0
    }

    fun size(): Int {
        return size
    }

    fun isEmpty(): Boolean {
        return size() == 0
    }

    override fun peekFirst(): T? {
        if (isEmpty()) throw RuntimeException("Empty list");
        return head?.data;
    }

    override fun peekLast(): T? {
        if (isEmpty()) throw RuntimeException("Empty list");
        return tail?.data;
    }

    override fun indexOf(obj: T): Int {
        var index = 0
        var trav = head

        // Support searching for null
        if (obj == null) {
            while (trav != null) {
                if (trav.data == null) {
                    return index
                }
                trav = trav.next
                index++
            }
            // Search for non null object
        } else while (trav != null) {
            if (obj == trav.data) {
                return index
            }
            trav = trav.next
            index++
        }
        return -1
    }


}