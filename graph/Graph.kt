package com.datastructure.kotlin.graph

import com.datastructure.kotlin.queue.Queue
import com.datastructure.kotlin.stack.Stack
import kotlin.collections.HashMap

class Graph<V> {
    public val adjacencyList: HashMap<V, MutableList<V>> = HashMap<V,MutableList<V>>()

    constructor(){

    }

    public fun addVertex(vertex: V){
        if(!adjacencyList.containsKey(vertex)) adjacencyList[vertex] = mutableListOf()
    }
    fun addEdge(v1: V, v2 : V){
        if(adjacencyList.containsKey(v1)) adjacencyList[v1]?.add(v2)
        if(adjacencyList.containsKey(v2)) adjacencyList[v2]?.add(v1)
    }

    fun removeEdge(v1: V, v2: V){
        if(adjacencyList.containsKey(v1)) adjacencyList[v1]?.remove(v2)
        if(adjacencyList.containsKey(v2)) adjacencyList[v2]?.remove(v1)
    }

    fun removeVertex(vertext: V)
    {
        while(!adjacencyList[vertext].isNullOrEmpty())
            removeEdge(vertext, adjacencyList[vertext]!![0])
        adjacencyList.remove(vertext)
    }

   /* fun depthFirstRecursive(startingVertex: V): MutableList<V>? {
        val result: MutableList<V> = mutableListOf()
        val visitedMap: HashMap<V, Boolean> = HashMap()

        return dfs(startingVertex, visitedMap, result)

    }

    fun dfs(vertex: V, visitedMap: HashMap<V, Boolean>, result: MutableList<V>): MutableList<V>? {
        if(vertex == null) return null
        visitedMap[vertex] = true
        result.add(vertex)
        val list: MutableList<V> = adjacencyList[vertex]!!
        for(neighbor in list){
            if(!visitedMap.containsKey(neighbor)) return dfs(neighbor,visitedMap, result)
        }

        return result
    }
*/

    fun depthFirstTraverse(startingVertex: V): MutableList<V> {
        val stack: Stack<V> = Stack<V>()
        stack.push(startingVertex)
        val result: MutableList<V> = mutableListOf()
        val visitedMap: HashMap<V, Boolean> = HashMap()
        var currentVertex: V? = null
        visitedMap[startingVertex] = true

    while (!stack.isEmpty()) {
        currentVertex = stack.pop()
        result.add(currentVertex)

        for (neighbor in adjacencyList[currentVertex]!!) {
            val value = visitedMap[neighbor]
            if (value == null) {
                visitedMap[neighbor] = true
                stack.push(neighbor)
            }
        }
    }
        return result

    }

        fun breadthFirstTraverse(startingVertex: V): MutableList<V> {
        val queue: Queue<V> = Queue<V>()
        queue.enqueue(startingVertex)
        val result: MutableList<V> = mutableListOf()
        val visitedMap: HashMap<V, Boolean> = HashMap()
        var currentVertex: V? = null
        visitedMap[startingVertex] = true

        while (!queue.isEmpty()){
            currentVertex = queue.dequeue()
            result.add(currentVertex)

            for(neighbor in adjacencyList[currentVertex]!!){
                var value = visitedMap[neighbor]
                if(value == null)
                {
                    visitedMap[neighbor] = true
                    queue.enqueue(neighbor)
                }
            }
        }

        return result

    }



    override fun toString(): String {
        var string: String = ""
        for(vertex in adjacencyList)
        {
            for(list in adjacencyList[vertex.key]!!)
            {
                string += "Key: ${vertex.key} & Value: ${list.toString()} \n"

            }
        }
        return string
    }
}