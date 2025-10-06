package algorithms;

import java.util.Objects;


public class BoyerMooreMajorityVote {

    /**
     * Metrics class to track algorithm performance
     */
    public static class Metrics {
        private int comparisons = 0;
        private int arrayAccesses = 0;
        private int memoryAllocations = 0;
        private long startTime = 0;
        private long endTime = 0;

        public void incrementComparisons() { comparisons++; }
        public void incrementArrayAccesses() { arrayAccesses++; }
        public void incrementMemoryAllocations() { memoryAllocations++; }
        public void startTimer() { startTime = System.nanoTime(); }
        public void endTimer() { endTime = System.nanoTime(); }

        public int getComparisons() { return comparisons; }
        public int getArrayAccesses() { return arrayAccesses; }
        public int getMemoryAllocations() { return memoryAllocations; }
        public long getExecutionTimeNanos() { return endTime - startTime; }
        public double getExecutionTimeMillis() { return (endTime - startTime) / 1_000_000.0; }

        @Override
        public String toString() {
            return String.format("Metrics{comparisons=%d, arrayAccesses=%d, memoryAllocations=%d, executionTime=%.3f ms}",
                    comparisons, arrayAccesses, memoryAllocations, getExecutionTimeMillis());
        }

        public void reset() {
            comparisons = 0;
            arrayAccesses = 0;
            memoryAllocations = 0;
            startTime = 0;
            endTime = 0;
        }
    }

    /**
     * Result class to encapsulate the algorithm result and metrics
     */
    public static class Result {
        private final Integer majorityElement;
        private final boolean hasMajority;
        private final Metrics metrics;
        private final String errorMessage;

        public Result(Integer majorityElement, boolean hasMajority, Metrics metrics) {
            this.majorityElement = majorityElement;
            this.hasMajority = hasMajority;
            this.metrics = metrics;
            this.errorMessage = null;
        }

        public Result(String errorMessage, Metrics metrics) {
            this.majorityElement = null;
            this.hasMajority = false;
            this.metrics = metrics;
            this.errorMessage = errorMessage;
        }

        public Integer getMajorityElement() { return majorityElement; }
        public boolean hasMajority() { return hasMajority; }
        public Metrics getMetrics() { return metrics; }
        public String getErrorMessage() { return errorMessage; }
        public boolean hasError() { return errorMessage != null; }

        @Override
        public String toString() {
            if (hasError()) {
                return String.format("Result{error='%s', %s}", errorMessage, metrics);
            }
            return String.format("Result{majorityElement=%s, hasMajority=%s, %s}",
                    majorityElement, hasMajority, metrics);
        }
    }


    public static Result findMajorityElement(Integer[] array) {
        Metrics metrics = new Metrics();
        metrics.startTimer();
        metrics.incrementMemoryAllocations(); // For metrics object

        try {
            // Input validation
            if (array == null) {
                metrics.endTimer();
                return new Result("Input array cannot be null", metrics);
            }

            metrics.incrementArrayAccesses(); // For null check

            // Handle empty array
            if (array.length == 0) {
                metrics.endTimer();
                return new Result(null, false, metrics);
            }

            metrics.incrementArrayAccesses(); // For length check

            // Handle single element array
            if (array.length == 1) {
                metrics.incrementArrayAccesses(); // Access array[0]
                metrics.endTimer();
                return new Result(array[0], true, metrics);
            }

            // First pass: Find candidate for majority element
            Integer candidate = findCandidate(array, metrics);

            // Second pass: Verify if candidate is actually majority
            boolean isMajority = verifyCandidate(array, candidate, metrics);

            metrics.endTimer();
            return new Result(candidate, isMajority, metrics);

        } catch (Exception e) {
            metrics.endTimer();
            return new Result("Unexpected error: " + e.getMessage(), metrics);
        }
    }


    private static Integer findCandidate(Integer[] array, Metrics metrics) {
        Integer candidate = null;
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            metrics.incrementArrayAccesses(); // Access array[i]

            if (count == 0) {
                candidate = array[i];
                count = 1;
            } else {
                metrics.incrementComparisons(); // Compare candidate with array[i]
                if (Objects.equals(candidate, array[i])) {
                    count++;
                } else {
                    count--;
                }
            }
        }

        return candidate;
    }


    private static boolean verifyCandidate(Integer[] array, Integer candidate, Metrics metrics) {
        if (candidate == null) {
            return false;
        }

        int count = 0;
        int majorityThreshold = array.length / 2 + 1;

        for (int i = 0; i < array.length; i++) {
            metrics.incrementArrayAccesses(); // Access array[i]
            metrics.incrementComparisons(); // Compare candidate with array[i]

            if (Objects.equals(candidate, array[i])) {
                count++;

                // Early termination optimization: if we've found enough occurrences,
                // we can stop early
                if (count >= majorityThreshold) {
                    return true;
                }
            }
        }

        return count > array.length / 2;
    }


    public static Integer[] generateTestArray(int size, boolean hasMajority) {
        if (size <= 0) {
            return new Integer[0];
        }

        Integer[] array = new Integer[size];

        if (hasMajority && size > 1) {
            // Create array with majority element
            int majorityCount = size / 2 + 1;
            int majorityElement = 1;

            // Fill majority positions
            for (int i = 0; i < majorityCount; i++) {
                array[i] = majorityElement;
            }

            // Fill remaining positions with different elements
            for (int i = majorityCount; i < size; i++) {
                array[i] = i - majorityCount + 2; // Different elements
            }
        } else {
            // Create array without majority element
            for (int i = 0; i < size; i++) {
                array[i] = i % (size / 2 + 1); // No element appears more than n/2 times
            }
        }

        return array;
    }


    public static void shuffleArray(Integer[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            // Swap elements
            Integer temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    public static String validateInput(Integer[] array) {
        if (array == null) {
            return "Input array cannot be null";
        }

        if (array.length == 0) {
            return "Input array is empty";
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return "Array contains null element at index " + i;
            }
        }

        return null;
    }
}
