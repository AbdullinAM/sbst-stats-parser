import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

val BENCHMARKS = Paths.get("benchmarks2022.list").toFile().readLines()
    .filter { it.isNotBlank() }.map { it.trim() }.sorted()

fun String.kapitalize() =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

data class Benchmark(
    val toolName: String = "",
    val benchmark: String = "",
    val cut: String = "",
    val runNumber: Int = 1,
    val prepTime: Int = 0,
    val genTime: Int = 0,
    val execTime: Int = 0,
    val testCaseNum: Int = 0,
    val uncompilableTestClasses: Int = 0,
    val brokenTests: Int = 0,
    val failingTests: Int = 0,
    val linesTotal: Int = 0,
    val linesCov: Int = 0,
    val linesCovRatio: Double = 0.0,
    val condTotal: Int = 0,
    val condCov: Int = 0,
    val condCovRatio: Double = 0.0,
    val mutTotal: Int = 0,
    val mutCov: Int = 0,
    val mutCovRatio: Double = 0.0,
    val mutKilled: Int = 0,
    val mutKilledRatio: Double = 0.0,
    val mutAlive: Int = 0
)

@OptIn(kotlin.ExperimentalStdlibApi::class)
fun parseBenchmarks(path: Path): List<Benchmark> = kotlin.collections.buildList<Benchmark> {
    var toolName: String = ""
    var benchmark: String = ""
    var cut: String = ""
    var runNumber: Int = 1
    var prepTime: Int = 0
    var genTime: Int = 0
    var execTime: Int = 0
    var testCaseNum: Int = 0
    var uncompilableTestClasses: Int = 0
    var brokenTests: Int = 0
    var failingTests: Int = 0
    var linesTotal: Int = 0
    var linesCov: Int = 0
    var linesCovRatio: Double = 0.0
    var condTotal: Int = 0
    var condCov: Int = 0
    var condCovRatio: Double = 0.0
    var mutTotal: Int = 0
    var mutCov: Int = 0
    var mutCovRatio: Double = 0.0
    var mutKilled: Int = 0
    var mutKilledRatio: Double = 0.0
    var mutAlive: Int = 0
    for (line in path.toFile().readLines()) {
        if (line.contains("Tool name:")) {
            add(
                Benchmark(
                    toolName,
                    benchmark,
                    cut,
                    runNumber,
                    prepTime,
                    genTime,
                    execTime,
                    testCaseNum,
                    uncompilableTestClasses,
                    brokenTests,
                    failingTests,
                    linesTotal,
                    linesCov,
                    linesCovRatio,
                    condTotal,
                    condCov,
                    condCovRatio,
                    mutTotal,
                    mutCov,
                    mutCovRatio,
                    mutKilled,
                    mutKilledRatio,
                    mutAlive
                )
            )
            toolName = line.split("Tool name:").last().trim()
            benchmark = ""
            cut = ""
            runNumber = 1
            prepTime = 0
            genTime = 0
            execTime = 0
            testCaseNum = 0
            uncompilableTestClasses = 0
            brokenTests = 0
            failingTests = 0
            linesTotal = 0
            linesCov = 0
            linesCovRatio = 0.0
            condTotal = 0
            condCov = 0
            condCovRatio = 0.0
            mutTotal = 0
            mutCov = 0
            mutCovRatio = 0.0
            mutKilled = 0
            mutKilledRatio = 0.0
            mutAlive = 0
        }

        if ("Benchmark:" in line) benchmark = line.split("Benchmark:").last().trim()
        if ("Class Under Test:" in line) cut = line.split("Class Under Test:").last().trim()
        if ("Run number:" in line) runNumber = line.split("Run number:").last().trim().toInt()
        if ("Tool preparation time (ms):" in line) prepTime =
            line.split("Tool preparation time (ms):").last().trim().toInt()
        if ("Tool test cases generation time (ms):" in line) genTime =
            line.split("Tool test cases generation time (ms):").last().trim().toInt()
        if ("Test cases execution time (ms):" in line) execTime =
            line.split("Test cases execution time (ms):").last().trim().toInt()
        if ("Test case number:" in line) testCaseNum = line.split("Test case number:").last().trim().toInt()
        if ("Uncompilable Test Classes:" in line) uncompilableTestClasses =
            line.split("Uncompilable Test Classes:").last().trim().toInt()
        if ("Broken tests number:" in line) brokenTests = line.split("Broken tests number:").last().trim().toInt()
        if ("Failing tests number:" in line) failingTests = line.split("Failing tests number:").last().trim().toInt()
        if ("Lines total:" in line) linesTotal = line.split("Lines total:").last().trim().toInt()
        if ("Lines covered:" in line) linesCov = line.split("Lines covered:").last().trim().toInt()
        if ("Lines coverage ratio (%):" in line) linesCovRatio =
            line.split("Lines coverage ratio (%):").last().trim().toDouble()
        if ("Conditions total:" in line) condTotal = line.split("Conditions total:").last().trim().toInt()
        if ("Conditions covered:" in line) condCov = line.split("Conditions covered:").last().trim().toInt()
        if ("Conditions coverage ratio (%):" in line) condCovRatio =
            line.split("Conditions coverage ratio (%):").last().trim().toDouble()
        if ("Mutants total:" in line) mutTotal = line.split("Mutants total:").last().trim().toInt()
        if ("Mutants covered:" in line) mutCov = line.split("Mutants covered:").last().trim().toInt()
        if ("Mutants coverage ratio (%):" in line) mutCovRatio =
            line.split("Mutants coverage ratio (%):").last().trim().toDouble()
        if ("Mutants killed:" in line) mutKilled = line.split("Mutants killed:").last().trim().toInt()
        if ("Mutants killed ratio (%):" in line) mutKilledRatio =
            line.split("Mutants killed ratio (%):").last().trim().toDouble()
        if ("Mutants alive:" in line) mutAlive = line.split("Mutants alive:").last().trim().toInt()
    }

    add(
        Benchmark(
            toolName,
            benchmark,
            cut,
            runNumber,
            prepTime,
            genTime,
            execTime,
            testCaseNum,
            uncompilableTestClasses,
            brokenTests,
            failingTests,
            linesTotal,
            linesCov,
            linesCovRatio,
            condTotal,
            condCov,
            condCovRatio,
            mutTotal,
            mutCov,
            mutCovRatio,
            mutKilled,
            mutKilledRatio,
            mutAlive
        )
    )
}.drop(1)

fun computeMetrics(
    name: String, vararg files: Path, metric: (Benchmark) -> Double = { it.linesCovRatio }
) {
    val results = files.mapNotNull { if (it.toFile().exists()) parseBenchmarks(it) else null }

    val avgs = results.map { it.map { metric(it) }.average() }

    for ((i, avg) in avgs.withIndex()) {
        println("${name.kapitalize()} $i: ${String.format("%.2f", avg)}")
    }

    val benchmarkMap = mutableMapOf<String, MutableList<Double>>()
    for (res in results) {
        for (b in res) {
            benchmarkMap.getOrPut(b.benchmark, ::mutableListOf).add(metric(b))
        }
    }

	val maxBenchmarkLength = BENCHMARKS.maxOf { it.length } + 1

    for (b in BENCHMARKS) {
        println(
            "$b${" ".repeat(maxBenchmarkLength - b.length)}\t${
                benchmarkMap.getOrDefault(b, results.map { 0.0 }.toMutableList())
                    .joinToString("\t") { String.format("%.2f", it) }
            }"
        )
    }
    println()
}

computeMetrics(
    "newKex",
    Paths.get("/home/abdullin/workspace/kex-sbst/stats.txt"),
    Paths.get("/home/abdullin/workspace/kex-sbst/statsSeed.txt"),
) { it.condCovRatio }

