# Thread Counter

## Program Description
Program that counts from 1 to N (500,000) using multiple threads/goroutines, distributing the work evenly among them.

### Test Characteristics
- **Maximum number to count**: 500,000
- **Metric**: Total execution time (seconds)
- **Output**: Printing each number to the console
- **Real Concurrency**: Multiple threads running simultaneously

## results

| Threads | Java (seconds) | Go (seconds) | Difference | Go's Advantage |
|-------|----------------|---------------|------------|---------------|
| 100   | 30.924259       | 2.302793      | 28.621466  | 13.4x faster |
| 200   | 32.194584       | 1.835646      | 30.358938  | 17.5x faster |
| 300   | 34.021969       | 1.715579      | 32.306390  | 19.8x faster |
| 400   | 45.409405       | 1.537877      | 43.871528  | 29.5x faster |
| 500   | 41.054192       | 1.709069      | 39.345123  | 24.0x faster |
| 1000  | 38.082384       | 1.371465      | 36.710919  | 27.8x faster |
| 5000  | 36.303140       | 1.165586      | 35.137554  | 31.2x faster |


### Key Metrics

| Métrica | Java | Go |
|---------|------|-----|
| **Average time** | 36.855 seconds | 1.662 seconds |
| **Minimum time** | 30,924 seconds (100 threads) | 1.166 seconds (5000 threads) |
| **Maximum time** | 45,409 seconds (400 threads) | 2.303 seconds (100 threads) |
| **Better performance** | 100 threads | 5000 threads |

## Conclusion on programs

Go is superior to Java for massive concurrency because its goroutines are ultra-lightweight (~2KB vs. ~1MB per thread in Java), allowing it to handle thousands of concurrent tasks without performance degradation. As the file demonstrates, Go is up to 31 times faster than Java with 5000 threads, and its performance even improves as concurrency increases, while Java slows down due to operating system overhead. For applications requiring high concurrency, intensive I/O, or microservices, Go is the clear superior choice.

## Conclusion regarding the number of threads

Using few or many threads isn't inherently better; it depends on the type of task. For pure CPU-bound computing, it's best to use a small number of threads, equal to the number of processor cores, because more threads only add overhead without improving performance. For I/O tasks (network, disk, console), many threads are beneficial because while some wait, others work, making better use of the CPU. As a general rule: if your threads perform a lot of calculations, use few; if they wait a lot, use many.