package algorithms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Boyer-Moore Majority Vote Algorithm Tests")
class BoyerMooreMajorityVoteTest {

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("Null input array")
        void testNullInput() {
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(null);

            assertTrue(result.hasError());
            assertEquals("Input array cannot be null", result.getErrorMessage());
            assertNull(result.getMajorityElement());
            assertFalse(result.hasMajority());
            assertNotNull(result.getMetrics());
        }

        @Test
        @DisplayName("Empty array")
        void testEmptyArray() {
            Integer[] array = new Integer[0];
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertNull(result.getMajorityElement());
            assertFalse(result.hasMajority());
            assertNotNull(result.getMetrics());
            assertTrue(result.getMetrics().getArrayAccesses() > 0);
        }

        @Test
        @DisplayName("Single element array")
        void testSingleElement() {
            Integer[] array = {42};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(42, result.getMajorityElement());
            assertTrue(result.hasMajority());
            assertNotNull(result.getMetrics());
        }

        @Test
        @DisplayName("Two different elements")
        void testTwoDifferentElements() {
            Integer[] array = {1, 2};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertFalse(result.hasMajority());
            assertNotNull(result.getMetrics());
        }

        @Test
        @DisplayName("Two same elements")
        void testTwoSameElements() {
            Integer[] array = {1, 1};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(1, result.getMajorityElement());
            assertTrue(result.hasMajority());
        }
    }

    @Nested
    @DisplayName("Arrays with Majority Elements")
    class ArraysWithMajority {

        @Test
        @DisplayName("Simple majority: [1,1,2]")
        void testSimpleMajority() {
            Integer[] array = {1, 1, 2};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(1, result.getMajorityElement());
            assertTrue(result.hasMajority());
        }

        @Test
        @DisplayName("Majority with duplicates: [2,2,1,1,1,2,2]")
        void testMajorityWithDuplicates() {
            Integer[] array = {2, 2, 1, 1, 1, 2, 2};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(2, result.getMajorityElement());
            assertTrue(result.hasMajority());
        }

        @Test
        @DisplayName("Large majority: [5,5,5,5,5,1,2,3,4]")
        void testLargeMajority() {
            Integer[] array = {5, 5, 5, 5, 5, 1, 2, 3, 4};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(5, result.getMajorityElement());
            assertTrue(result.hasMajority());
        }

        @Test
        @DisplayName("Negative numbers majority: [-1,-1,-1,2,3]")
        void testNegativeNumbersMajority() {
            Integer[] array = {-1, -1, -1, 2, 3};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(-1, result.getMajorityElement());
            assertTrue(result.hasMajority());
        }

        @Test
        @DisplayName("Zero majority: [0,0,0,1,2]")
        void testZeroMajority() {
            Integer[] array = {0, 0, 0, 1, 2};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertEquals(0, result.getMajorityElement());
            assertTrue(result.hasMajority());
        }
    }

    @Nested
    @DisplayName("Arrays without Majority Elements")
    class ArraysWithoutMajority {

        @Test
        @DisplayName("No majority: [1,2,3]")
        void testNoMajorityThreeElements() {
            Integer[] array = {1, 2, 3};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertFalse(result.hasMajority());
        }

        @Test
        @DisplayName("Equal distribution: [1,1,2,2]")
        void testEqualDistribution() {
            Integer[] array = {1, 1, 2, 2};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertFalse(result.hasMajority());
        }

        @Test
        @DisplayName("All different: [1,2,3,4,5]")
        void testAllDifferent() {
            Integer[] array = {1, 2, 3, 4, 5};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertFalse(result.hasMajority());
        }

        @Test
        @DisplayName("Almost majority: [1,1,1,2,2,3]")
        void testAlmostMajority() {
            Integer[] array = {1, 1, 1, 2, 2, 3};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertFalse(result.hasMajority());
        }
    }

    @Nested
    @DisplayName("Performance Metrics")
    class PerformanceMetrics {

        @Test
        @DisplayName("Metrics are collected correctly")
        void testMetricsCollection() {
            Integer[] array = {1, 1, 2, 1, 3, 1, 4};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            BoyerMooreMajorityVote.Metrics metrics = result.getMetrics();

            assertNotNull(metrics);
            assertTrue(metrics.getComparisons() > 0);
            assertTrue(metrics.getArrayAccesses() > 0);
            assertTrue(metrics.getExecutionTimeNanos() > 0);
            assertTrue(metrics.getExecutionTimeMillis() > 0);
            assertTrue(metrics.getMemoryAllocations() > 0);
        }

        @Test
        @DisplayName("Metrics reset functionality")
        void testMetricsReset() {
            BoyerMooreMajorityVote.Metrics metrics = new BoyerMooreMajorityVote.Metrics();

            metrics.incrementComparisons();
            metrics.incrementArrayAccesses();
            metrics.incrementMemoryAllocations();
            metrics.startTimer();
            metrics.endTimer();

            assertTrue(metrics.getComparisons() > 0);
            assertTrue(metrics.getArrayAccesses() > 0);
            assertTrue(metrics.getMemoryAllocations() > 0);
            assertTrue(metrics.getExecutionTimeNanos() >= 0);

            metrics.reset();

            assertEquals(0, metrics.getComparisons());
            assertEquals(0, metrics.getArrayAccesses());
            assertEquals(0, metrics.getMemoryAllocations());
            assertEquals(0, metrics.getExecutionTimeNanos());
        }

        @ParameterizedTest
        @ValueSource(ints = {10, 100, 1000, 10000})
        @DisplayName("Performance scales linearly with input size")
        void testPerformanceScaling(int size) {
            Integer[] array = BoyerMooreMajorityVote.generateTestArray(size, true);
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertTrue(result.hasMajority());

            BoyerMooreMajorityVote.Metrics metrics = result.getMetrics();

            // Array accesses should be approximately 2 * size (first pass + verification)
            assertTrue(metrics.getArrayAccesses() >= size);
            assertTrue(metrics.getArrayAccesses() <= 3 * size); // Allow some overhead

            // Comparisons should be roughly proportional to size
            assertTrue(metrics.getComparisons() >= size / 2);
            assertTrue(metrics.getComparisons() <= 2 * size);
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class InputValidation {

        @Test
        @DisplayName("Validate null input")
        void testValidateNullInput() {
            String result = BoyerMooreMajorityVote.validateInput(null);
            assertEquals("Input array cannot be null", result);
        }

        @Test
        @DisplayName("Validate empty input")
        void testValidateEmptyInput() {
            String result = BoyerMooreMajorityVote.validateInput(new Integer[0]);
            assertEquals("Input array is empty", result);
        }

        @Test
        @DisplayName("Validate array with null elements")
        void testValidateArrayWithNullElements() {
            Integer[] array = {1, null, 3};
            String result = BoyerMooreMajorityVote.validateInput(array);
            assertEquals("Array contains null element at index 1", result);
        }

        @Test
        @DisplayName("Validate valid input")
        void testValidateValidInput() {
            Integer[] array = {1, 2, 3, 4, 5};
            String result = BoyerMooreMajorityVote.validateInput(array);
            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Utility Methods")
    class UtilityMethods {

        @Test
        @DisplayName("Generate test array with majority")
        void testGenerateTestArrayWithMajority() {
            Integer[] array = BoyerMooreMajorityVote.generateTestArray(10, true);

            assertEquals(10, array.length);

            // Count occurrences of the majority element (should be 1)
            int count = 0;
            for (Integer element : array) {
                if (element.equals(1)) {
                    count++;
                }
            }

            assertTrue(count > 5); // Should be majority
        }

        @Test
        @DisplayName("Generate test array without majority")
        void testGenerateTestArrayWithoutMajority() {
            Integer[] array = BoyerMooreMajorityVote.generateTestArray(10, false);

            assertEquals(10, array.length);

            // Verify no element appears more than n/2 times
            for (int i = 0; i < 10; i++) {
                int count = 0;
                for (Integer element : array) {
                    if (element.equals(i)) {
                        count++;
                    }
                }
                assertTrue(count <= 5); // No element should be majority
            }
        }

        @Test
        @DisplayName("Generate empty test array")
        void testGenerateEmptyTestArray() {
            Integer[] array = BoyerMooreMajorityVote.generateTestArray(0, true);
            assertEquals(0, array.length);
        }

        @Test
        @DisplayName("Shuffle array maintains elements")
        void testShuffleArray() {
            Integer[] original = {1, 2, 3, 4, 5};
            Integer[] shuffled = original.clone();

            BoyerMooreMajorityVote.shuffleArray(shuffled);

            // Arrays should have same length
            assertEquals(original.length, shuffled.length);

            // Arrays should contain same elements (but possibly in different order)
            java.util.Arrays.sort(original);
            java.util.Arrays.sort(shuffled);
            assertArrayEquals(original, shuffled);
        }

        @Test
        @DisplayName("Shuffle null array")
        void testShuffleNullArray() {
            assertDoesNotThrow(() -> BoyerMooreMajorityVote.shuffleArray(null));
        }

        @Test
        @DisplayName("Shuffle empty array")
        void testShuffleEmptyArray() {
            Integer[] array = new Integer[0];
            assertDoesNotThrow(() -> BoyerMooreMajorityVote.shuffleArray(array));
        }
    }

    @Nested
    @DisplayName("Complex Scenarios")
    class ComplexScenarios {

        @Test
        @DisplayName("Large array with majority")
        void testLargeArrayWithMajority() {
            Integer[] array = BoyerMooreMajorityVote.generateTestArray(10000, true);
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertTrue(result.hasMajority());
            assertEquals(1, result.getMajorityElement());
        }

        @Test
        @DisplayName("Large array without majority")
        void testLargeArrayWithoutMajority() {
            Integer[] array = BoyerMooreMajorityVote.generateTestArray(10000, false);
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertFalse(result.hasMajority());
        }

        @Test
        @DisplayName("Array with all same elements")
        void testArrayWithAllSameElements() {
            Integer[] array = new Integer[100];
            java.util.Arrays.fill(array, 42);

            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertTrue(result.hasMajority());
            assertEquals(42, result.getMajorityElement());
        }

        @Test
        @DisplayName("Alternating pattern")
        void testAlternatingPattern() {
            Integer[] array = {1, 2, 1, 2, 1, 2, 1};
            BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

            assertFalse(result.hasError());
            assertTrue(result.hasMajority());
            assertEquals(1, result.getMajorityElement());
        }
    }

}
