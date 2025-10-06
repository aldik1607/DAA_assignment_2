package org.example;

import algorithms.BoyerMooreMajorityVote;
import cli.BenchmarkRunner;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        System.out.println("=== Boyer-Moore Majority Vote Algorithm Implementation ===");
        System.out.println("DAA Assignment 2");
        System.out.println();

        if (args.length > 0) {
            handleCommandLineArguments(args);
        } else {
            runInteractiveMode();
        }
    }

    /**
     * Handle command line arguments
     */
    private static void handleCommandLineArguments(String[] args) {
        String command = args[0].toLowerCase();

        switch (command) {
            case "demo" -> runDemo();
            case "cli" -> runCLI();
            case "test" -> runBasicTests();
            case "benchmark" -> runQuickBenchmark();
            case "help" -> displayHelp();
            default -> {
                System.out.println("Unknown command: " + command);
                displayHelp();
            }
        }
    }

    /**
     * Run interactive mode with menu
     */
    private static void runInteractiveMode() {
        System.out.println("Choose an option:");
        System.out.println("1. Run Demo");
        System.out.println("2. Start CLI Interface");
        System.out.println("3. Run Basic Tests");
        System.out.println("4. Run Quick Benchmark");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");

        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> runDemo();
                case 2 -> runCLI();
                case 3 -> runBasicTests();
                case 4 -> runQuickBenchmark();
                case 5 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice. Please run the program again.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please run the program again.");
        }
    }

    /**
     * Run demonstration of the algorithm
     */
    private static void runDemo() {
        System.out.println("\n=== Algorithm Demonstration ===");

        // Example 1: Array with majority element
        System.out.println("\n1. Array with majority element:");
        Integer[] array1 = {1, 1, 2, 1, 3, 1, 4};
        System.out.println("Input: " + Arrays.toString(array1));
        BoyerMooreMajorityVote.Result result1 = BoyerMooreMajorityVote.findMajorityElement(array1);
        System.out.println("Result: " + result1);

        // Example 2: Array without majority element
        System.out.println("\n2. Array without majority element:");
        Integer[] array2 = {1, 2, 3, 4, 5};
        System.out.println("Input: " + Arrays.toString(array2));
        BoyerMooreMajorityVote.Result result2 = BoyerMooreMajorityVote.findMajorityElement(array2);
        System.out.println("Result: " + result2);

        // Example 3: Array with negative numbers
        System.out.println("\n3. Array with negative numbers:");
        Integer[] array3 = {-1, -1, -1, 2, 3};
        System.out.println("Input: " + Arrays.toString(array3));
        BoyerMooreMajorityVote.Result result3 = BoyerMooreMajorityVote.findMajorityElement(array3);
        System.out.println("Result: " + result3);

        // Example 4: Edge case - single element
        System.out.println("\n4. Edge case - single element:");
        Integer[] array4 = {42};
        System.out.println("Input: " + Arrays.toString(array4));
        BoyerMooreMajorityVote.Result result4 = BoyerMooreMajorityVote.findMajorityElement(array4);
        System.out.println("Result: " + result4);

        // Example 5: Edge case - empty array
        System.out.println("\n5. Edge case - empty array:");
        Integer[] array5 = {};
        System.out.println("Input: " + Arrays.toString(array5));
        BoyerMooreMajorityVote.Result result5 = BoyerMooreMajorityVote.findMajorityElement(array5);
        System.out.println("Result: " + result5);
    }

    /**
     * Start the CLI interface
     */
    private static void runCLI() {
        System.out.println("\n=== Starting CLI Interface ===");
        BenchmarkRunner runner = new BenchmarkRunner();
        try {
            runner.run();
        } finally {
            runner.close();
        }
    }

    /**
     * Run basic tests
     */
    private static void runBasicTests() {
        System.out.println("\n=== Running Basic Tests ===");

        // Test 1: Validation
        System.out.println("\n1. Input Validation Tests:");
        System.out.println("Null input: " + BoyerMooreMajorityVote.validateInput(null));
        System.out.println("Empty input: " + BoyerMooreMajorityVote.validateInput(new Integer[0]));
        System.out.println("Valid input: " + BoyerMooreMajorityVote.validateInput(new Integer[]{1, 2, 3}));

        // Test 2: Array generation
        System.out.println("\n2. Array Generation Tests:");
        Integer[] testArray1 = BoyerMooreMajorityVote.generateTestArray(10, true);
        System.out.println("Array with majority (size 10): " + Arrays.toString(testArray1));

        Integer[] testArray2 = BoyerMooreMajorityVote.generateTestArray(10, false);
        System.out.println("Array without majority (size 10): " + Arrays.toString(testArray2));

        // Test 3: Performance on different sizes
        System.out.println("\n3. Performance Tests:");
        int[] sizes = {100, 1000, 10000};
        for (int size : sizes) {
            Integer[] testArray = BoyerMooreMajorityVote.generateTestArray(size, true);
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(testArray);
            BoyerMooreMajorityVote.Metrics metrics = result.getMetrics();
            System.out.printf("Size %d: %d comparisons, %d accesses, %.3f ms%n",
                    size, metrics.getComparisons(), metrics.getArrayAccesses(), metrics.getExecutionTimeMillis());
        }
    }

    /**
     * Run quick benchmark
     */
    private static void runQuickBenchmark() {
        System.out.println("\n=== Quick Benchmark ===");

        cli.BenchmarkRunner runner = new cli.BenchmarkRunner();
        runner.runDemo();
        runner.close();
    }

    /**
     * Display help information
     */
    private static void displayHelp() {
        System.out.println("\n=== Help ===");
        System.out.println("Usage: java -jar DAA_assignment_2.jar [command]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  demo       - Run algorithm demonstration");
        System.out.println("  cli        - Start interactive CLI interface");
        System.out.println("  test       - Run basic tests");
        System.out.println("  benchmark  - Run quick benchmark");
        System.out.println("  help       - Display this help message");
        System.out.println();
        System.out.println("If no command is provided, an interactive menu will be displayed.");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar DAA_assignment_2.jar demo");
        System.out.println("  java -jar DAA_assignment_2.jar cli");
        System.out.println("  java -jar DAA_assignment_2.jar test");
    }
}
