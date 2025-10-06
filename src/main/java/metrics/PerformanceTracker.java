package metrics;

import algorithms.BoyerMooreMajorityVote;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class PerformanceTracker {


    public static class PerformanceResult {
        private final int inputSize;
        private final long executionTimeNanos;
        private final int comparisons;
        private final int arrayAccesses;
        private final int memoryAllocations;
        private final boolean hasMajority;
        private final String algorithmName;
        private final long timestamp;

        public PerformanceResult(int inputSize, long executionTimeNanos, int comparisons,
                                 int arrayAccesses, int memoryAllocations, boolean hasMajority,
                                 String algorithmName) {
            this.inputSize = inputSize;
            this.executionTimeNanos = executionTimeNanos;
            this.comparisons = comparisons;
            this.arrayAccesses = arrayAccesses;
            this.memoryAllocations = memoryAllocations;
            this.hasMajority = hasMajority;
            this.algorithmName = algorithmName;
            this.timestamp = System.currentTimeMillis();
        }

        public int getInputSize() { return inputSize; }
        public long getExecutionTimeNanos() { return executionTimeNanos; }
        public double getExecutionTimeMillis() { return executionTimeNanos / 1_000_000.0; }
        public int getComparisons() { return comparisons; }
        public int getArrayAccesses() { return arrayAccesses; }
        public int getMemoryAllocations() { return memoryAllocations; }
        public boolean hasMajority() { return hasMajority; }
        public String getAlgorithmName() { return algorithmName; }
        public long getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return String.format("PerformanceResult{size=%d, time=%.3f ms, comparisons=%d, accesses=%d, allocations=%d, hasMajority=%s, algorithm='%s'}",
                    inputSize, getExecutionTimeMillis(), comparisons, arrayAccesses, memoryAllocations, hasMajority, algorithmName);
        }
    }


    public static class PerformanceSummary {
        private final String algorithmName;
        private final int inputSize;
        private final int runCount;
        private final double avgExecutionTime;
        private final double minExecutionTime;
        private final double maxExecutionTime;
        private final double stdDevExecutionTime;
        private final double avgComparisons;
        private final double avgArrayAccesses;
        private final double avgMemoryAllocations;
        private final List<PerformanceResult> results;

        public PerformanceSummary(String algorithmName, int inputSize, List<PerformanceResult> results) {
            this.algorithmName = algorithmName;
            this.inputSize = inputSize;
            this.runCount = results.size();
            this.results = new ArrayList<>(results);

            // Calculate statistics
            double[] times = results.stream().mapToDouble(PerformanceResult::getExecutionTimeMillis).toArray();
            this.avgExecutionTime = Arrays.stream(times).average().orElse(0.0);
            this.minExecutionTime = Arrays.stream(times).min().orElse(0.0);
            this.maxExecutionTime = Arrays.stream(times).max().orElse(0.0);
            this.stdDevExecutionTime = calculateStandardDeviation(times, avgExecutionTime);

            this.avgComparisons = results.stream().mapToInt(PerformanceResult::getComparisons).average().orElse(0.0);
            this.avgArrayAccesses = results.stream().mapToInt(PerformanceResult::getArrayAccesses).average().orElse(0.0);
            this.avgMemoryAllocations = results.stream().mapToInt(PerformanceResult::getMemoryAllocations).average().orElse(0.0);
        }

        private double calculateStandardDeviation(double[] values, double mean) {
            double variance = Arrays.stream(values)
                    .map(x -> Math.pow(x - mean, 2))
                    .average()
                    .orElse(0.0);
            return Math.sqrt(variance);
        }

        public String getAlgorithmName() { return algorithmName; }
        public int getInputSize() { return inputSize; }
        public int getRunCount() { return runCount; }
        public double getAvgExecutionTime() { return avgExecutionTime; }
        public double getMinExecutionTime() { return minExecutionTime; }
        public double getMaxExecutionTime() { return maxExecutionTime; }
        public double getStdDevExecutionTime() { return stdDevExecutionTime; }
        public double getAvgComparisons() { return avgComparisons; }
        public double getAvgArrayAccesses() { return avgArrayAccesses; }
        public double getAvgMemoryAllocations() { return avgMemoryAllocations; }
        public List<PerformanceResult> getResults() { return new ArrayList<>(results); }

        @Override
        public String toString() {
            return String.format("PerformanceSummary{algorithm='%s', size=%d, runs=%d, avgTime=%.3f±%.3f ms, " +
                            "min=%.3f ms, max=%.3f ms, avgComparisons=%.1f, avgAccesses=%.1f, avgAllocations=%.1f}",
                    algorithmName, inputSize, runCount, avgExecutionTime, stdDevExecutionTime,
                    minExecutionTime, maxExecutionTime, avgComparisons, avgArrayAccesses, avgMemoryAllocations);
        }
    }

    private final Map<String, List<PerformanceResult>> results;

    public PerformanceTracker() {
        this.results = new ConcurrentHashMap<>();
    }


    public List<PerformanceResult> benchmarkBoyerMoore(int inputSize, boolean hasMajority, int runs) {
        List<PerformanceResult> runResults = new ArrayList<>();

        for (int i = 0; i < runs; i++) {
            // Generate test array
            Integer[] testArray = BoyerMooreMajorityVote.generateTestArray(inputSize, hasMajority);

            // Shuffle for randomization
            BoyerMooreMajorityVote.shuffleArray(testArray);

            // Run algorithm and measure performance
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(testArray);

            if (!result.hasError()) {
                BoyerMooreMajorityVote.Metrics metrics = result.getMetrics();
                PerformanceResult perfResult = new PerformanceResult(
                        inputSize,
                        metrics.getExecutionTimeNanos(),
                        metrics.getComparisons(),
                        metrics.getArrayAccesses(),
                        metrics.getMemoryAllocations(),
                        result.hasMajority(),
                        "BoyerMooreMajorityVote"
                );

                runResults.add(perfResult);
            }
        }

        // Store results
        String key = "BoyerMooreMajorityVote_" + inputSize + "_" + hasMajority;
        results.computeIfAbsent(key, k -> new ArrayList<>()).addAll(runResults);

        return runResults;
    }


    public Map<Integer, PerformanceSummary> comprehensiveBenchmark(int[] inputSizes, boolean hasMajority, int runs) {
        Map<Integer, PerformanceSummary> summaries = new LinkedHashMap<>();

        for (int size : inputSizes) {
            List<PerformanceResult> runResults = benchmarkBoyerMoore(size, hasMajority, runs);
            PerformanceSummary summary = new PerformanceSummary("BoyerMooreMajorityVote", size, runResults);
            summaries.put(size, summary);
        }

        return summaries;
    }


    public Map<String, Map<Integer, PerformanceSummary>> compareMajorityVsNoMajority(int[] inputSizes, int runs) {
        Map<String, Map<Integer, PerformanceSummary>> comparison = new LinkedHashMap<>();

        // Test with majority elements
        comparison.put("WithMajority", comprehensiveBenchmark(inputSizes, true, runs));

        // Test without majority elements
        comparison.put("WithoutMajority", comprehensiveBenchmark(inputSizes, false, runs));

        return comparison;
    }


    public List<PerformanceResult> getResults(String algorithmName) {
        List<PerformanceResult> allResults = new ArrayList<>();
        for (List<PerformanceResult> resultList : results.values()) {
            if (!resultList.isEmpty() && resultList.get(0).getAlgorithmName().equals(algorithmName)) {
                allResults.addAll(resultList);
            }
        }
        return allResults;
    }


    public PerformanceSummary getSummary(String algorithmName, int inputSize) {
        List<PerformanceResult> algorithmResults = getResults(algorithmName);
        List<PerformanceResult> sizeResults = algorithmResults.stream()
                .filter(r -> r.getInputSize() == inputSize)
                .toList();

        return sizeResults.isEmpty() ? null : new PerformanceSummary(algorithmName, inputSize, sizeResults);
    }

    /**
     * Clear all stored results
     */
    public void clearResults() {
        results.clear();
    }

    /**
     * Export results to CSV format
     *
     * @return CSV string representation of all results
     */
    public String exportToCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("Algorithm,InputSize,ExecutionTimeMs,Comparisons,ArrayAccesses,MemoryAllocations,HasMajority,Timestamp\n");

        for (List<PerformanceResult> resultList : results.values()) {
            for (PerformanceResult result : resultList) {
                csv.append(String.format("%s,%d,%.6f,%d,%d,%d,%s,%d\n",
                        result.getAlgorithmName(),
                        result.getInputSize(),
                        result.getExecutionTimeMillis(),
                        result.getComparisons(),
                        result.getArrayAccesses(),
                        result.getMemoryAllocations(),
                        result.hasMajority(),
                        result.getTimestamp()
                ));
            }
        }

        return csv.toString();
    }


    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== Performance Analysis Report ===\n\n");

        // Group results by algorithm
        Map<String, List<PerformanceResult>> algorithmResults = new HashMap<>();
        for (List<PerformanceResult> resultList : results.values()) {
            for (PerformanceResult result : resultList) {
                algorithmResults.computeIfAbsent(result.getAlgorithmName(), k -> new ArrayList<>()).add(result);
            }
        }

        for (Map.Entry<String, List<PerformanceResult>> entry : algorithmResults.entrySet()) {
            String algorithm = entry.getKey();
            List<PerformanceResult> results = entry.getValue();

            report.append(String.format("Algorithm: %s\n", algorithm));
            report.append(String.format("Total runs: %d\n", results.size()));

            // Group by input size
            Map<Integer, List<PerformanceResult>> sizeGroups = new TreeMap<>();
            for (PerformanceResult result : results) {
                sizeGroups.computeIfAbsent(result.getInputSize(), k -> new ArrayList<>()).add(result);
            }

            for (Map.Entry<Integer, List<PerformanceResult>> sizeEntry : sizeGroups.entrySet()) {
                int size = sizeEntry.getKey();
                List<PerformanceResult> sizeResults = sizeEntry.getValue();
                PerformanceSummary summary = new PerformanceSummary(algorithm, size, sizeResults);

                report.append(String.format("  Input size %d: %s\n", size, summary));
            }

            report.append("\n");
        }

        return report.toString();
    }


    public String getMemoryUsageStats() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();

        return String.format("Memory Usage: Used=%d MB, Free=%d MB, Total=%d MB, Max=%d MB",
                usedMemory / (1024 * 1024),
                freeMemory / (1024 * 1024),
                totalMemory / (1024 * 1024),
                maxMemory / (1024 * 1024));
    }
}
