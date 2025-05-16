package serie2.part3

class Node<T>(
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null
) {
}


fun splitEvensAndOdds(list: Node<Int>) {
    var current = list.next
    val sentinel = list

    while (current != sentinel) {
        val nextNode = current?.next
        if (current?.value!! % 2 == 0) {
            current.previous?.next = current.next
            current.next?.previous = current.previous
            current.next = sentinel.next
            current.previous = sentinel
            sentinel.next?.previous = current
            sentinel.next = current
        }
        current = nextNode
    }
}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    if (list1.next == list1 || list2.next == list2) return null
    var node1 = list1.next
    var node2 = list2.next
    var result: Node<T>? = null
    var last: Node<T>? = result

    while (node1 != list1 && node2 != list2) {
        val compare = cmp.compare(node1!!.value, node2!!.value)
        when {
            compare == 0 -> {
                val next1 = node1.next
                val next2 = node2.next
                node1.previous?.next = node1.next
                node1.next?.previous = node1.previous
                node2.previous?.next = node2.next
                node2.next?.previous = node2.previous
                if (result == null) {
                    result = node1
                    last = node1
                    node1.previous = null
                } else {
                    last?.next = node1
                    node1.previous = last
                    last = node1
                }
                last.next = null
                node1 = next1
                node2 = next2
            }
            compare < 0 -> {
                node1 = node1.next
            }
            else -> {
                node2 = node2.next
            }
        }
    }
    return result
}

