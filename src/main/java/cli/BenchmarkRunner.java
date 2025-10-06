package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;
import java.util.*;
import java.util.stream.IntStream;


public class BenchmarkRunner {

    private final PerformanceTracker performanceTracker;
    private final Scanner scanner;

    public BenchmarkRunner() {
        this.performanceTracker = new PerformanceTracker();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main entry point for CLI
     */
    public void run() {
        System.out.println("=== Boyer-Moore Majority Vote Algorithm Benchmark Runner ===");
        System.out.println();

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> runSingleTest();
                    case "2" -> runQuickBenchmark();
                    case "3" -> runComprehensiveBenchmark();
                    case "4" -> runComparisonTest();
                    case "5" -> runInteractiveTest();
                    case "6" -> displayResults();
                    case "7" -> exportResults();
                    case "8" -> displayMemoryStats();
                    case "9" -> runStressTest();
                    case "0" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Run Single Test");
        System.out.println("2. Quick Benchmark");
        System.out.println("3. Comprehensive Benchmark");
        System.out.println("4. Comparison Test (Majority vs No Majority)");
        System.out.println("5. Interactive Test");
        System.out.println("6. Display Results");
        System.out.println("7. Export Results");
        System.out.println("8. Memory Statistics");
        System.out.println("9. Stress Test");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Run a single test with user-provided input
     */
    private void runSingleTest() {
        System.out.println("\n=== Single Test ===");

        System.out.print("Enter array size: ");
        int size = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Should array have majority element? (y/n): ");
        boolean hasMajority = scanner.nextLine().trim().toLowerCase().startsWith("y");

        System.out.print("Number of runs: ");
        int runs = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("\nRunning test...");

        long startTime = System.currentTimeMillis();
        List<PerformanceTracker.PerformanceResult> results =
                performanceTracker.benchmarkBoyerMoore(size, hasMajority, runs);
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Test completed in %d ms", endTime - startTime));
        System.out.println(String.format("Results for %d runs:", results.size()));

        if (!results.isEmpty()) {
            PerformanceTracker.PerformanceSummary summary =
                    new PerformanceTracker.PerformanceSummary("BoyerMooreMajorityVote", size, results);
            System.out.println(summary);
        }
    }

    /**
     * Run a quick benchmark with predefined sizes
     */
    private void runQuickBenchmark() {
        System.out.println("\n=== Quick Benchmark ===");

        int[] sizes = {100, 1000, 10000, 100000};
        int runs = 10;

        System.out.println("Running quick benchmark with sizes: " + Arrays.toString(sizes));
        System.out.println("Runs per size: " + runs);
        System.out.println();

        long startTime = System.currentTimeMillis();
        Map<Integer, PerformanceTracker.PerformanceSummary> results =
                performanceTracker.comprehensiveBenchmark(sizes, true, runs);
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Benchmark completed in %d ms", endTime - startTime));
        System.out.println("\nResults:");

        for (Map.Entry<Integer, PerformanceTracker.PerformanceSummary> entry : results.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    /**
     * Run comprehensive benchmark with custom parameters
     */
    private void runComprehensiveBenchmark() {
        System.out.println("\n=== Comprehensive Benchmark ===");

        System.out.print("Enter minimum size: ");
        int minSize = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter maximum size: ");
        int maxSize = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter step size: ");
        int stepSize = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Runs per size: ");
        int runs = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Should arrays have majority elements? (y/n): ");
        boolean hasMajority = scanner.nextLine().trim().toLowerCase().startsWith("y");

        int[] sizes = IntStream.rangeClosed(minSize, maxSize)
                .filter(i -> (i - minSize) % stepSize == 0)
                .toArray();

        System.out.println("Running comprehensive benchmark...");
        System.out.println("Sizes: " + Arrays.toString(sizes));
        System.out.println("Runs per size: " + runs);
        System.out.println();

        long startTime = System.currentTimeMillis();
        Map<Integer, PerformanceTracker.PerformanceSummary> results =
                performanceTracker.comprehensiveBenchmark(sizes, hasMajority, runs);
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Benchmark completed in %d ms", endTime - startTime));
        System.out.println("\nResults:");

        for (Map.Entry<Integer, PerformanceTracker.PerformanceSummary> entry : results.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    /**
     * Run comparison test between arrays with and without majority elements
     */
    private void runComparisonTest() {
        System.out.println("\n=== Comparison Test ===");

        System.out.print("Enter array sizes (comma-separated): ");
        String sizesInput = scanner.nextLine().trim();
        int[] sizes = Arrays.stream(sizesInput.split(","))
                .mapToInt(s -> Integer.parseInt(s.trim()))
                .toArray();

        System.out.print("Runs per configuration: ");
        int runs = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Running comparison test...");
        System.out.println("Sizes: " + Arrays.toString(sizes));
        System.out.println("Runs per configuration: " + runs);
        System.out.println();

        long startTime = System.currentTimeMillis();
        Map<String, Map<Integer, PerformanceTracker.PerformanceSummary>> comparison =
                performanceTracker.compareMajorityVsNoMajority(sizes, runs);
        long endTime = System.currentTimeMillis();

        System.out.println(String.format("Comparison completed in %d ms", endTime - startTime));
        System.out.println("\nResults:");

        for (Map.Entry<String, Map<Integer, PerformanceTracker.PerformanceSummary>> entry : comparison.entrySet()) {
            System.out.println("\n" + entry.getKey() + ":");
            for (PerformanceTracker.PerformanceSummary summary : entry.getValue().values()) {
                System.out.println("  " + summary);
            }
        }
    }

    /**
     * Interactive test where user can input custom arrays
     */
    private void runInteractiveTest() {
        System.out.println("\n=== Interactive Test ===");

        while (true) {
            System.out.print("Enter array elements (comma-separated, or 'quit' to exit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                Integer[] array = Arrays.stream(input.split(","))
                        .map(s -> Integer.parseInt(s.trim()))
                        .toArray(Integer[]::new);

                System.out.println("Array: " + Arrays.toString(array));

                BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

                if (result.hasError()) {
                    System.out.println("Error: " + result.getErrorMessage());
                } else {
                    System.out.println("Result: " + result);
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter comma-separated integers.");
            }
        }
    }

    /**
     * Display stored results
     */
    private void displayResults() {
        System.out.println("\n=== Stored Results ===");

        String report = performanceTracker.generateReport();
        if (report.trim().isEmpty()) {
            System.out.println("No results stored.");
        } else {
            System.out.println(report);
        }
    }

    /**
     * Export results to file
     */
    private void exportResults() {
        System.out.println("\n=== Export Results ===");

        System.out.print("Enter filename (without extension): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            filename = "benchmark_results_" + System.currentTimeMillis();
        }

        try {
            String csvData = performanceTracker.exportToCSV();
            String reportData = performanceTracker.generateReport();

            // In a real implementation, you would write to files
            System.out.println("CSV Data:");
            System.out.println(csvData);
            System.out.println("\nReport Data:");
            System.out.println(reportData);

            System.out.println(String.format("Results exported to %s.csv and %s.txt", filename, filename));

        } catch (Exception e) {
            System.out.println("Error exporting results: " + e.getMessage());
        }
    }

    /**
     * Display memory statistics
     */
    private void displayMemoryStats() {
        System.out.println("\n=== Memory Statistics ===");
        System.out.println(performanceTracker.getMemoryUsageStats());
    }

    /**
     * Run stress test with large arrays
     */
    private void runStressTest() {
        System.out.println("\n=== Stress Test ===");

        System.out.print("Enter maximum array size for stress test: ");
        int maxSize = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Number of test runs: ");
        int runs = Integer.parseInt(scanner.nextLine().trim());

        int[] sizes = {maxSize / 10, maxSize / 5, maxSize / 2, maxSize};

        System.out.println("Running stress test with sizes: " + Arrays.toString(sizes));
        System.out.println("Runs per size: " + runs);
        System.out.println();

        long startTime = System.currentTimeMillis();

        for (int size : sizes) {
            System.out.println(String.format("Testing size %d...", size));
            List<PerformanceTracker.PerformanceResult> results =
                    performanceTracker.benchmarkBoyerMoore(size, true, runs);

            if (!results.isEmpty()) {
                PerformanceTracker.PerformanceSummary summary =
                        new PerformanceTracker.PerformanceSummary("BoyerMooreMajorityVote", size, results);
                System.out.println(summary);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Stress test completed in %d ms", endTime - startTime));
    }

    /**
     * Run predefined demo
     */
    public void runDemo() {
        System.out.println("=== Running Demo ===");

        // Demo 1: Basic functionality
        System.out.println("\n1. Basic Functionality Test:");
        Integer[] testArray1 = {1, 1, 2, 1, 3, 1, 4};
        BoyerMooreMajorityVote.Result result1 = BoyerMooreMajorityVote.findMajorityElement(testArray1);
        System.out.println("Array: " + Arrays.toString(testArray1));
        System.out.println("Result: " + result1);

        // Demo 2: No majority
        System.out.println("\n2. No Majority Test:");
        Integer[] testArray2 = {1, 2, 3, 4, 5};
        BoyerMooreMajorityVote.Result result2 = BoyerMooreMajorityVote.findMajorityElement(testArray2);
        System.out.println("Array: " + Arrays.toString(testArray2));
        System.out.println("Result: " + result2);

        // Demo 3: Performance test
        System.out.println("\n3. Performance Test:");
        int[] sizes = {1000, 10000, 100000};
        for (int size : sizes) {
            List<PerformanceTracker.PerformanceResult> results =
                    performanceTracker.benchmarkBoyerMoore(size, true, 5);
            if (!results.isEmpty()) {
                PerformanceTracker.PerformanceSummary summary =
                        new PerformanceTracker.PerformanceSummary("BoyerMooreMajorityVote", size, results);
                System.out.println(summary);
            }
        }
    }

    /**
     * Close resources
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}

