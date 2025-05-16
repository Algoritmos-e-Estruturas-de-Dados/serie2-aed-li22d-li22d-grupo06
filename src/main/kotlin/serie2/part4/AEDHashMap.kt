package serie2.part4

import java.util.NoSuchElementException

class AEDHashMap<K, V>(
    initialCapacity: Int = 16, val loadFactor: Float = 0.75f,
    override var size: Int = 0,
    override var capacity: Int = initialCapacity
) : MutableMap<K, V> {
    private class HashNode<K, V>(
        override val key: K, override var value: V,
        var next: HashNode<K, V>? = null
    ) : MutableMap.MutableEntry<K, V> {
        var hc = key.hashCode()
        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }

    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)


    private fun expand() {
        val newCapacity = capacity * 2
        val newTable: Array<HashNode<K, V>?> = arrayOfNulls(newCapacity)
        for (i in table.indices) {
            var node = table[i]
            while (node != null) {
                val idx = node.hc % newCapacity
                val nextNode = node.next
                node.next = newTable[idx]
                newTable[idx] = node
                node = nextNode
            }
        }
        table = newTable
        capacity = newCapacity
    }

    override fun get(key: K): V? {
        val idx = key.hashCode() % table.size
        var node = table[idx]
        while (node != null) {
            if (node.key == key) {
                return node.value
            }
            node = node.next
        }
        return null
    }

    override fun put(key: K, value: V): V? {
        if (size * loadFactor >= capacity) {
            expand()
        }
        val idx = key.hashCode() % table.size
        var node = table[idx]
        while (node != null) {
            if (node.key == key) {
                val oldValue = node.value
                node.value = value
                return oldValue
            }
            node = node.next
        }
        size++
        table[idx] = HashNode(key, value, table[idx])
        return null
    }

    private inner class MyIterator : Iterator<MutableMap.MutableEntry<K, V>> {
        var currIdx = -1;
        var currNode: HashNode<K, V>? = null
        var list: HashNode<K, V>? = null

        override fun hasNext(): Boolean {
            if (currNode != null) return true
            while (currIdx < capacity) {
                if (list == null) {
                    currIdx++
                    if (currIdx < capacity) list = table[currIdx]
                } else {
                    currNode = list
                    list?.let { l -> list = l.next }
                    return true
                }
            }
            return false;
        }

        override fun next(): MutableMap.MutableEntry<K, V> {
            if (!hasNext()) throw NoSuchElementException()
            val aux = currNode
            currNode = null
            return aux ?: Any() as MutableMap.MutableEntry<K, V>
        }
    }

    override fun iterator(): Iterator<MutableMap.MutableEntry<K, V>> = MyIterator()

}