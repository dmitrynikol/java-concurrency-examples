package main.java.org.dmitrynikol;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue solving the producer-consumer problem - 
 * http://en.wikipedia.org/wiki/Producer-consumer_problem
 * Consumer thread will automatically wait until BlockingQueue is not populated with some data. 
 * After filling, thread will consume the data
 * 
 * @author Dmitry Nikolaenko
 */
public class Main {
	public static void main(String[] args) {
		int threadCounter = 0;
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(75);

		TestThreadPoolExecutor executor = 
				new TestThreadPoolExecutor(5, 20, 5000, TimeUnit.MILLISECONDS, blockingQueue);

		// Handler help us to solve task rejection.
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				TestThread thread = ((TestThread) r);
				System.out.println("TestThread rejected : " + thread.getName());
				System.out.println("Just waiting for a second!");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// A second attempt to complete the task.
				System.out.println("Execute one more time: " + thread.getName());
				executor.execute(r);
			}
		});
		
		// Starts all core threads, causing them to idly wait for work.
		executor.prestartAllCoreThreads();
		while (true) {
			threadCounter++;
			
			// Executes the given task sometime in the future. 
			// The task may execute in a new thread or in an existing pooled thread. 
			// If the task cannot be submitted for execution it will be handled by the RejectedExecutionHandler.
			System.out.println("Adding TestThread: " + threadCounter);
			executor.execute(new TestThread(String.valueOf(threadCounter)));

			if (threadCounter == 100) {
				break;
			}
		}
	}
}