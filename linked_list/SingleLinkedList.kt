package com.datastructure.kotlin.linked_list

data class SingleDirectionNode<T>(var data: T, var next: SingleDirectionNode<T>?)


class SingleLinkedList<T> : LinkedList<T>() {
    private var size = 0
    private var head: SingleDirectionNode<T?>? = null
    private var tail: SingleDirectionNode<T?>? = null

    override fun add(obj: T) {
        addLast(obj)
    }

    override fun addFirst(obj: T) {
        if (isEmpty()) {
            val node = SingleDirectionNode<T?>(obj, null)
            head = node
            tail = node
        } else {
            val node = SingleDirectionNode<T?>(obj, head)
            head = node
        }
        size++
    }

    override fun addLast(obj: T) {
        if (isEmpty()) {
            val node = SingleDirectionNode<T?>(obj, null)
            head = node
            tail = node
        } else {
            tail?.next = SingleDirectionNode<T?>(obj, null)
            tail = tail?.next
        }

        size++
    }

    fun remove(obj: SingleDirectionNode<T?>?): T? {
        if (obj == head) return removeFirst()
        if (obj?.next == null) return removeLast()

        val data: T? = obj.data
        var trav = head
        var trav2 = head?.next
        while (trav != null)
        {
            if(trav2 == obj)
            {
                trav.next = trav2.next
                return data
            }

            trav = trav.next
            trav2 = trav2?.next
        }
        obj.data = null
        obj.next = null
        --size
        return data
    }

    override fun remove(obj: T): Boolean {
        var trav: SingleDirectionNode<T?>? = head

        if (obj == null) {
            while (trav != null) {
                if (trav.data == null) {
                    remove(trav)
                    return true
                }
                trav = trav.next
            }
        } else {
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
        return data
    }

    override fun removeLast(): T? {
        if (isEmpty()) throw RuntimeException("Empty list")

        val data: T? = tail?.data
        var trav = head

        while (trav?.next != tail)
        {
            trav = trav?.next
        }

        trav?.next = null
        tail = trav
        --size

        if (isEmpty()) head = null
        else tail?.next = null

        return data
    }

    override fun contains(obj: T): Boolean {
        return indexOf(obj) != -1;
    }

    override fun clear() {
        var trav: SingleDirectionNode<T?>? = head
        while (trav != null) {
            val next = trav.next
            trav.apply {
                this.next = null
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