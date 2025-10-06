# Boyer-Moore Majority Vote Algorithm Implementation


A comprehensive implementation of the **Boyer-Moore Majority Vote Algorithm** with performance analysis, benchmarking capabilities, and an interactive command-line interface.

## 🎯 Overview

The Boyer-Moore Majority Vote Algorithm is an efficient algorithm for finding the majority element in an array. A majority element is defined as an element that appears more than n/2 times in an array of size n.

### Key Features
- **Time Complexity**: O(n) - linear time
- **Space Complexity**: O(1) - constant space
- **Two-pass algorithm**: First pass finds a candidate, second pass verifies it
- **Early termination optimization** in the verification phase
- **Comprehensive performance tracking** with detailed metrics
- **Interactive CLI** for testing and benchmarking
- **Extensive test suite** covering all edge cases

## 🏗️ Project Structure

```
DAA_assignment_2/
├── src/main/java/
│   ├── algorithms/
│   │   └── BoyerMooreMajorityVote.java    # Core algorithm implementation
│   ├── metrics/
│   │   └── PerformanceTracker.java        # Performance analysis and benchmarking
│   ├── cli/
│   │   └── BenchmarkRunner.java           # Interactive command-line interface
│   └── Main.java                          # Application entry point
├── src/test/java/
│   └── algorithms/
│       └── BoyerMooreMajorityVoteTest.java # Comprehensive test suite
├── docs/
│   └── performance-plots/                 # Performance analysis plots
├── pom.xml                                # Maven configuration
└── README.md                              # This file
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Building the Project
```bash
# Clone the repository
git clone https://github.com/aldik1607/DAA_assignment_2.git
cd DAA_assignment_2

# Build the project
mvn clean compile

# Run tests
mvn test

# Run the application
mvn exec:java -Dexec.mainClass="Main"
```

## 💻 Usage Examples

### Basic Algorithm Usage
```java
import algorithms.BoyerMooreMajorityVote;

// Example array with majority element
Integer[] array = {1, 1, 2, 1, 3, 1, 4};
BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

if (result.hasMajority()) {
    System.out.println("Majority element: " + result.getMajorityElement());
} else {
    System.out.println("No majority element found");
}

// Access performance metrics
BoyerMooreMajorityVote.Metrics metrics = result.getMetrics();
System.out.println("Comparisons: " + metrics.getComparisons());
System.out.println("Array accesses: " + metrics.getArrayAccesses());
System.out.println("Execution time: " + metrics.getExecutionTimeMillis() + " ms");
```

### Performance Benchmarking
```java
import metrics.PerformanceTracker;

PerformanceTracker tracker = new PerformanceTracker();

// Run benchmark with different input sizes
int[] sizes = {1000, 10000, 100000};
Map<Integer, PerformanceTracker.PerformanceSummary> results = 
    tracker.comprehensiveBenchmark(sizes, true, 10);

// Display results
for (PerformanceTracker.PerformanceSummary summary : results.values()) {
    System.out.println(summary);
}

// Export to CSV
String csvData = tracker.exportToCSV();
```

### Command Line Interface
```bash
# Run with different modes
java -jar DAA_assignment_2.jar demo       # Run demonstration
java -jar DAA_assignment_2.jar cli        # Start interactive CLI
java -jar DAA_assignment_2.jar test       # Run basic tests
java -jar DAA_assignment_2.jar benchmark  # Run quick benchmark
java -jar DAA_assignment_2.jar help       # Display help
```

## 🔧 CLI Features

The interactive CLI provides the following options:

1. **Single Test** - Test with custom array size and parameters
2. **Quick Benchmark** - Run predefined benchmark with common sizes
3. **Comprehensive Benchmark** - Custom benchmark with user-defined parameters
4. **Comparison Test** - Compare performance with and without majority elements
5. **Interactive Test** - Test with custom arrays
6. **Stress Test** - Test with large arrays
7. **Export Results** - Export results to CSV format
8. **Memory Statistics** - Display memory usage information

## 📊 Performance Analysis

### Algorithm Complexity
- **Time Complexity**: O(n) - linear time complexity
- **Space Complexity**: O(1) - constant space complexity
- **Best Case**: O(n) - when majority element is found early
- **Average Case**: O(n) - linear scan through array
- **Worst Case**: O(n) - full array scan required

### Performance Metrics
The implementation tracks:
- **Comparisons**: Number of element comparisons performed
- **Array Accesses**: Number of array element accesses
- **Memory Allocations**: Number of memory allocations
- **Execution Time**: Precise timing in nanoseconds and milliseconds

### CSV Export
Results can be exported to CSV format with the following columns:
- Algorithm name
- Input size
- Execution time (milliseconds)
- Number of comparisons
- Array accesses
- Memory allocations
- Whether majority element exists
- Timestamp

## 🧪 Testing

### Test Coverage
The comprehensive test suite covers:

#### Edge Cases
- Null input arrays
- Empty arrays
- Single element arrays
- Two element arrays (with and without majority)

#### Normal Cases
- Arrays with majority elements
- Arrays without majority elements
- Arrays with negative numbers
- Arrays with zero values
- Large arrays (up to 100,000 elements)

#### Performance Tests
- Linear scaling verification
- Memory usage validation
- Execution time consistency
- Statistical analysis of multiple runs

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BoyerMooreMajorityVoteTest

# Run tests with detailed output
mvn test -Dtest=BoyerMooreMajorityVoteTest -Dmaven.test.failure.ignore=true
```

## 🔍 Algorithm Details

### How It Works
1. **First Pass**: Find a candidate for the majority element using voting mechanism
2. **Second Pass**: Verify if the candidate is actually the majority element

### Optimizations
1. **Early Termination**: The verification phase stops early when majority threshold is reached
2. **Efficient Candidate Selection**: Uses voting mechanism to find potential majority
3. **Memory Efficient**: Uses only O(1) extra space
4. **Null-Safe Comparisons**: Uses `Objects.equals()` for safe element comparison

### Error Handling
- Null input validation
- Empty array handling
- Null element detection
- Exception handling with detailed error messages
- Graceful degradation for edge cases

## 📈 Benchmarking Results

The algorithm demonstrates excellent performance characteristics:
- Linear time complexity across all input sizes
- Consistent performance regardless of data distribution
- Minimal memory overhead
- Reliable results with statistical significance

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is part of a DAA (Design and Analysis of Algorithms) assignment and is for educational purposes.

## 👨‍💻 Author

**DAA Assignment 2** - Boyer-Moore Majority Vote Algorithm Implementation

---

## 🔗 Useful Links

- [Boyer-Moore Majority Vote Algorithm](https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm)
- [Maven Documentation](https://maven.apache.org/guides/)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)

## 📞 Support

If you have any questions or issues, please open an issue on the [GitHub repository](https://github.com/aldik1607/DAA_assignment_2/issues).
