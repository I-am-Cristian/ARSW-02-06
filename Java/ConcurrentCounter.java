import java.util.Scanner;

/**
 * A simple concurrent program that counts from 1 to N using multiple threads.
 * The work is distributed evenly among the threads.
 */
public class ConcurrentCounter {
    
    /**
     * Thread class responsible for counting a specific range of numbers.
     */
    static class CounterThread extends Thread {
        private int start;  
        private int end;  
        
        /**
         * Constructor for CounterThread.
         * @param start The first number this thread will count
         * @param end The last number this thread will count
         */
        public CounterThread(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        /**
         * The main logic executed when the thread starts.
         * Prints all numbers from start to end (inclusive).
         */
        public void run() {
            for (int i = start; i <= end; i++) {
                System.out.println(i);
            }
        }
    }
    
    /**
     * Main method - entry point of the program.
     * @param args 
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get user input for the maximum number to count to
        System.out.print("Enter maximum number: ");
        int maxNumber = scanner.nextInt();
        
        System.out.print("Enter number of threads: ");
        int threadCount = scanner.nextInt();
        
        scanner.close();
        
        long startTime = System.nanoTime();
        
        CounterThread[] threads = new CounterThread[threadCount];
        

        int numbersPerThread = maxNumber / threadCount;
        
        int currentNumber = 1;
        
        /**
         * WORK DISTRIBUTION LOGIC:
         * - First (threadCount - 1) threads get exactly 'numbersPerThread' numbers
         * - Last thread gets the remaining numbers (base count + any remainder)
         * This ensures all numbers from 1 to maxNumber are covered
         */
        for (int i = 0; i < threadCount; i++) {
            int start = currentNumber;  
            int end;                
            
            if (i == threadCount - 1) {
                end = maxNumber;
            } else {
                end = currentNumber + numbersPerThread - 1;
            }
            threads[i] = new CounterThread(start, end);
            currentNumber = end + 1;
        }
        
        /**
         * THREAD STARTING LOGIC:
         * Iterate through all threads and start them.
         * The start() method begins concurrent execution.
         */
        for (CounterThread thread : threads) {
            thread.start();
        }
        
        /**
         * THREAD SYNCHRONIZATION LOGIC:
         * Use join() to wait for each thread to complete.
         * join() makes the main thread pause until the specified thread finishes.
         * This ensures all counting is done before measuring the final time.
         */
        try {
            for (CounterThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        long endTime = System.nanoTime();
        
        double totalTime = (endTime - startTime) / 1_000_000_000.0;
        
        System.out.printf("\nTime: %.6f seconds%n", totalTime);
    }
}