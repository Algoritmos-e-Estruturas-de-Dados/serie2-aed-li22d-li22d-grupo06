package serie2.part4

class AEDHashMap<K, V>(
    initialCapacity: Int = 16, val loadFactor: Float = 0.75f,
    override var size: Int = 0,
    override val capacity: Int = initialCapacity
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
        TODO()
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
        if (size.toFloat() / capacity >= loadFactor) {
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

    override fun iterator(): Iterator<MutableMap.MutableEntry<K, V>> {
        return object : Iterator<MutableMap.MutableEntry<K, V>> {
            var current = 0
            override fun hasNext(): Boolean = current < size
            override fun next(): MutableMap.MutableEntry<K, V> {
                if (!hasNext()) throw NoSuchElementException()
                val node = table[current]
                current++
                return node ?: throw NoSuchElementException()
            }
        }
    }
}