package main

import (
	"fmt"
	"sync"
	"time"
)

// CounterThread is a goroutine that counts a specific range of numbers
type CounterThread struct {
	start int
	end   int
	wg    *sync.WaitGroup
	mu    *sync.Mutex
}

// Run executes the counter logic with thread-safe output
func (c CounterThread) Run() {
	defer c.wg.Done()

	// Each goroutine builds its own string and then writes atomically
	var output string

	for i := c.start; i <= c.end; i++ {
		if i == c.start {
			output = fmt.Sprintf("%d", i)
		} else {
			output = fmt.Sprintf("%s\n%d", output, i)
		}
	}

	// Lock and write safely
	c.mu.Lock()
	fmt.Println(output)
	c.mu.Unlock()
}

func main() {
	var maxNumber int
	var threadCount int

	// Get user input
	fmt.Print("Enter maximum number: ")
	fmt.Scan(&maxNumber)

	fmt.Print("Enter number of threads: ")
	fmt.Scan(&threadCount)

	// Record start time
	startTime := time.Now()

	var wg sync.WaitGroup
	var mu sync.Mutex

	numbersPerThread := maxNumber / threadCount
	currentNumber := 1

	// Work distribution
	for i := 0; i < threadCount; i++ {
		start := currentNumber
		var end int

		if i == threadCount-1 {
			end = maxNumber
		} else {
			end = currentNumber + numbersPerThread - 1
		}

		wg.Add(1)

		// Launch goroutine with mutex for safe output
		counter := CounterThread{
			start: start,
			end:   end,
			wg:    &wg,
			mu:    &mu,
		}
		go counter.Run()

		currentNumber = end + 1
	}

	// Wait for all goroutines to complete
	wg.Wait()

	// Calculate and display execution time
	elapsed := time.Since(startTime)
	fmt.Printf("\nTime: %.6f seconds\n", elapsed.Seconds())
}
