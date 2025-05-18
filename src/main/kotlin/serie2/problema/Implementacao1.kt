package serie2.problema

class ProcessPointsCollections {
    private var p1 = emptySet<Point>()
    private var p2 = emptySet<Point>()

    fun loadDocument(file1: String, file2: String) {
        p1 = parsePoints(file1)
        p2 = parsePoints(file2)
        println("Loaded ${p1.size} points from $file1 and ${p2.size} from $file2.")
    }

    fun unionDocuments(outputFile: String) {
        writePoints(p1 union p2, outputFile)
        println("Union written to $outputFile")
    }

    fun intersectionDocuments(outputFile: String) {
        writePoints(p1 intersect p2, outputFile)
        println("Intersection written to $outputFile")
    }

    fun differenceDocuments(outputFile: String) {
        writePoints(p1 subtract p2, outputFile)
        println("Difference written to $outputFile")
    }
}

fun help() {
    println("Available commands:")
    println("-------------------------------------------------------------------------------")
    println("load <file1.co> <file2.co>      - Loads points from two .co files")
    println("union <output.co>               - Saves the union of points to output.co")
    println("intersection <output.co>        - Saves the intersection of points to output.co")
    println("difference <output.co>          - Saves the points from file1 not in file2")
    println("exit                            - Closes the application")
    println("help                            - Shows this help message")
    println("-------------------------------------------------------------------------------")
    println("Notes:")
    println("- Only lines starting with 'v' are considered (others are ignored).")
    println("- Point IDs are ignored; only coordinates are used.")
    println("- Coordinates are treated as (x, y) floats.")
    println("- Results exclude duplicate points.")
    println()
    println("Example usage:")
    println("load pointsA.co pointsB.co")
    println("union result.co")
    println("exit")
}

fun main() {
    val processor = ProcessPointsCollections()
    println("Welcome to ProcessPointsCollections application")
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