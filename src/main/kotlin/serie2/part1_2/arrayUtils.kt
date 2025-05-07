package serie2.part1_2
import kotlin.random.Random

fun minimum(maxHeap: Array<Int>, heapSize: Int): Int {
    if (heapSize == 0) throw Exception("Heap is empty")
    if (heapSize == 1) return maxHeap[0]
    val mid = heapSize / 2
    var min = maxHeap[mid]
    for (i in mid until heapSize) {
        if (maxHeap[i] < min) {
            min = maxHeap[i]
        }
    }
    return min
}
