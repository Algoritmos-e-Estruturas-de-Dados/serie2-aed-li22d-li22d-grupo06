package serie2.problema

data class Point(val x: Float, val y: Float)

/**
 * This function reads a file and parses points from it.
 * The file is expected to contain lines starting with "v" followed by coordinates.
 * Each point is represented as a pair of floats.
 * We use Set to store unique points.
 *
 * @param fileName The name of the file to read.
 * @return A set of points parsed from the file.
 */

fun parsePoints(fileName: String): Set<Point> {
    val reader = createReader(fileName)
    val points = mutableSetOf<Point>()
    reader.useLines { lines ->
        lines.forEach { line ->
            if (line.startsWith("v ")) {
                val parts = line.split(" ")
                if (parts.size == 4) {
                    val x = parts[2].toFloat() // first coordinate
                    val y = parts[3].toFloat() // second coordinate
                    points.add(Point(x, y))
                }
            }
        }
    }
    return points
}

/**
 * This function writes a set of points to a file.
 * Each point is written in the format "v id x y", where id is the index of the point.
 *
 * @param points The set of points to write.
 * @param fileName The name of the file to write to.
 */

fun writePoints(points: Set<Point>, fileName: String) {
    val writer = createWriter(fileName)
    var id = 1
    points.forEach { point ->
        writer.println("v $id ${point.x} ${point.y}")
        id++
    }
    writer.close()
}