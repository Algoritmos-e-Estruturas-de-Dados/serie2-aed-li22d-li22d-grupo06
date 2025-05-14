package serie2.part1_2

class IntArrayList(val capacity: Int) : Iterable<Int> {
    val intList = IntArray(capacity)
    var size = 0
    var tail = 0
    var head = 0

    fun append(x: Int): Boolean {
        if (size == capacity) return false
        intList[tail] = x
        tail = (tail + 1) % capacity
        size++
        return true
    }

    fun get(n: Int): Int? {
        if (n !in 0 until size) return null
        return intList[(head + n) % capacity]
    }

    fun addToAll(x: Int) {
        for (i in 0 until size) {
            val index = (head + i) % capacity
            intList[index] += x
        }
    }

    fun remove(): Boolean {
        if (size == 0) return false
        head = (head + 1) % capacity
        size--
        return true
    }

    override fun iterator(): Iterator<Int> {
        return object : Iterator<Int> {
            var current = 0
            override fun hasNext(): Boolean = current < size
            override fun next(): Int {
                if (!hasNext()) throw NoSuchElementException()
                return intList[(head + current++) % capacity]
            }
        }
    }
}