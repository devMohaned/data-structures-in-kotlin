package com.datastructure.kotlin.hashmap

import java.util.*
import kotlin.math.max


class Entry<K, V>(var key: K, var value: V) {
    var hash: Int = key.hashCode()

    // It's not the override equals method
    fun equals(other: Entry<K, V>): Boolean {
        if (hash != other.hash) return false
        return key?.equals(other.key) ?: false
    }
    override fun toString(): String = "$key  =>  $value"
}

private const val DEFAULT_CAPACITY: Int = 3
private const val DEFAULT_LOAD_FACTOR: Double = 0.75
class MapWithSeparateChaining<K,V> {

    private var maxLoadFactor: Double = 0.0
    private var capacity: Int = 0
    private var size: Int = 0
    private var threshold: Int
    private var table: Array<LinkedList<Entry<K,V>>?>

    constructor() :  this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR)

    constructor(capacity: Int) : this(capacity, DEFAULT_LOAD_FACTOR)

    @Suppress("UNCHECKED_CAST") // Annoying Cast
    constructor(capacity: Int, maxLoadFactor: Double) {
        if(capacity < 0 ) throw IllegalArgumentException("Illegal Capacity")
        if(maxLoadFactor <= 0
            || java.lang.Double.isNaN(maxLoadFactor)
            || java.lang.Double.isInfinite(maxLoadFactor)) throw IllegalArgumentException("Illegal MaxLoader")

        this.maxLoadFactor = maxLoadFactor
        this.capacity = max(DEFAULT_CAPACITY, capacity)
        threshold = (this.capacity * maxLoadFactor).toInt()
        table = arrayOfNulls<LinkedList<Entry<K,V>?>>(this.capacity) as Array<LinkedList<Entry<K,V>>?>
    }

    fun size(): Int = size
    fun isEmpty(): Boolean = size == 0

    // Converts a hash value to an index. Essentially, this strips the
    // negative sign and places the hash value in the domain [0, capacity)
    private fun normalizeIndex(keyHash: Int): Int = (keyHash and 0x7FFFFFFF) % capacity

    fun clear() {
        Arrays.fill(table, null)
        size = 0
    }

    fun containsKey(key: K): Boolean = hasKey(key)


    // Returns true/false depending on whether a key is in the hash table
    fun hasKey(key: K): Boolean {
        val bucketIndex = normalizeIndex(key.hashCode())
        return bucketSeekEntry(bucketIndex, key) != null
    }

    // Insert, put and add all place a value in the hash-table
    fun put(key: K, value: V): V? {
        return insert(key, value)
    }

    fun add(key: K, value: V): V? {
        return insert(key, value)
    }

    fun insert(key: K?, value: V): V? {
        if(key == null) throw NullPointerException("Key Cannot be null")
        val newEntry = Entry<K, V>(key, value)
        val bucketIndex = normalizeIndex(newEntry.hash)
        return bucketInsertEntry(bucketIndex, newEntry)
    }

    // Gets a key's values from the map and returns the value.
    // NOTE: returns null if the value is null AND also returns
    // null if the key does not exists, so watch out..
    fun get(key: K?): V? {
        if (key == null) return null
        val bucketIndex = normalizeIndex(key.hashCode())
        val entry: Entry<K, V>? = bucketSeekEntry(bucketIndex, key)
        return if (entry != null) entry.value else null
    }

    // Removes a key from the map and returns the value.
    // NOTE: returns null if the value is null AND also returns
    // null if the key does not exists.
    fun remove(key: K?): V? {
        if (key == null) return null
        val bucketIndex = normalizeIndex(key.hashCode())
        return bucketRemoveEntry(bucketIndex, key)
    }

    // Removes an entry from a given bucket if it exists
    private fun bucketRemoveEntry(bucketIndex: Int, key: K): V? {
        val entry: Entry<K, V>? = bucketSeekEntry(bucketIndex, key)
        return if (entry != null) {
            val links: LinkedList<Entry<K, V>>? = table[bucketIndex]
            links?.remove(entry)
            --size
            entry.value
        } else null
    }

    // Inserts an entry in a given bucket only if the entry does not already
    // exist in the given bucket, but if it does then update the entry value
    private fun bucketInsertEntry(bucketIndex: Int, entry: Entry<K, V>): V? {
        var bucket: LinkedList<Entry<K, V>>? = table[bucketIndex]
        if (bucket == null) {
            bucket = LinkedList()
            table[bucketIndex] = bucket
        }
        val existentEntry = bucketSeekEntry(bucketIndex, entry.key)
        return if (existentEntry == null) {
            bucket.add(entry)
            if (++size > threshold) resizeTable()
            null // Use null to indicate that there was no previous entry
        } else {
            val oldVal = existentEntry.value
            existentEntry.value = entry.value
            oldVal
        }
    }

    // Finds and returns a particular entry in a given bucket if it exists, returns null otherwise
    private fun bucketSeekEntry(bucketIndex: Int, key: K?): Entry<K, V>? {
        if (key == null) return null
        val bucket = table[bucketIndex]
            ?: return null
        for (entry in bucket) if (entry.key!!.equals(key)) return entry
        return null
    }

    // Resizes the internal table holding buckets of entries
    private fun resizeTable() {
        capacity *= 2
        threshold = (capacity * maxLoadFactor).toInt()
        val newTable: Array<LinkedList<Entry<K, V>>?> = arrayOfNulls(capacity)
        for (i in table.indices) {
            if (table[i] != null) {
                for (entry in table[i]!!) {
                    val bucketIndex = normalizeIndex(entry.hash)
                    var bucket = newTable[bucketIndex]
                    if (bucket == null) {
                        bucket = LinkedList()
                        newTable[bucketIndex] = bucket
                    }
                    bucket.add(entry)
                }

                // Avoid memory leak. Help the GC
                table[i]?.clear()
                table[i] = null
            }
        }
        table = newTable
    }

    // Returns the list of keys found within the hash table
    fun keys(): List<K> {
        val keys: MutableList<K> = ArrayList(size())
        for (bucket in table) if (bucket != null) for (entry in bucket) keys.add(entry.key)
        return keys
    }

    // Returns the list of values found within the hash table
    fun values(): List<V> {
        val values: MutableList<V> = ArrayList(size())
        for (bucket in table) if (bucket != null) for (entry in bucket) values.add(entry.value)
        return values
    }

}