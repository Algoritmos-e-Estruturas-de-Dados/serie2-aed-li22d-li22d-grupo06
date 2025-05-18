package serie2.problema

import serie2.part4.AEDHashMap

/**
 * This class processes two collections of points.
 *
 * It allows loading points from files, performing set operations (union, intersection, difference),
 * and writing the results to output files.
 *
 * The points are represented as pairs of floats (x, y).
 *
 * The class uses AEDHashMap to store points, which allows for efficient operations.
 *
 * The points are expected to be in the format "v id x y" in the input files,
 *
 * where "id" is ignored and "x" and "y" are the coordinates of the point.
 *
 * The output files will contain the points in the same format.
 *
 * The class provides methods to load points from files, write points to files,
 * and perform set operations on the loaded points.
 *
 * The main function provides a simple command-line interface for the user to interact with the class.
 */
class AEDProcessPointsCollections {
    private val p1 = AEDHashMap<Point, Boolean>()
    private val p2 = AEDHashMap<Point, Boolean>()

    fun loadIntoMap(fileName: String, map: AEDHashMap<Point, Boolean>) {
        val reader = createReader(fileName)
        reader.useLines { lines ->
            lines.forEach { line ->
                if (line.startsWith("v ")) {
                    val parts = line.split(" ")
                    if (parts.size == 4) {
                        val x = parts[2].toFloat()
                        val y = parts[3].toFloat()
                        map.put(Point(x, y), true)
                    }
                }
            }
        }
    }

    fun writePoints(map: AEDHashMap<Point, Boolean>, outputFile: String) {
        val writer = createWriter(outputFile)
        var id = 1
        for (entry in map) {
            val point = entry.key
            writer.println("v $id ${point.x} ${point.y}")
            id++
        }
        writer.close()
    }

    fun loadDocument(file1: String, file2: String) {
        loadIntoMap(file1, p1)
        loadIntoMap(file2, p2)
        println("Loaded ${p1.size} points from $file1 and ${p2.size} from $file2.")
    }


    fun unionDocuments(outputFile: String) {
        val union = AEDHashMap<Point, Boolean>()
        for (entry in p1) union.put(entry.key, true)
        for (entry in p2) union.put(entry.key, true)
        writePoints(union, outputFile)
        println("Union written to $outputFile")
    }

    fun intersectionDocuments(outputFile: String) {
        val result = AEDHashMap<Point, Boolean>()
        for (entry in p1) {
            if (p2.get(entry.key) != null) {
                result.put(entry.key, true)
            }
        }
        writePoints(result, outputFile)
        println("Intersection written to $outputFile")
    }

    fun differenceDocuments(outputFile: String) {
        val result = AEDHashMap<Point, Boolean>()
        for (entry in p1) {
            if (p2.get(entry.key) == null) {
                result.put(entry.key, true)
            }
        }
        writePoints(result, outputFile)
        println("Difference written to $outputFile")
    }
}

fun main() {
    val processor = AEDProcessPointsCollections()
    println("Welcome to AEDProcessPointsCollections application")
    println("Type 'help' for available commands.")
    while (true) {
        print("> ")
        val tokens = readln().trim().split(" ") // Split the input into tokens
        when {
            tokens[0] == "load" && tokens.size == 3 -> processor.loadDocument(tokens[1], tokens[2])
            tokens[0] == "union" && tokens.size == 2 -> processor.unionDocuments(tokens[1])
            tokens[0] == "intersection" && tokens.size == 2 -> processor.intersectionDocuments(tokens[1])
            tokens[0] == "difference" && tokens.size == 2 -> processor.differenceDocuments(tokens[1])
            tokens[0] == "help" -> help()
            tokens[0] == "exit" -> break
            else -> println("Invalid command. Type 'help' for usage.")
        }
    }
}