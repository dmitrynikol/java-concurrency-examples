package main.java.org.dmitrynikol;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Comparison between: BlockingThreadPoolExecutor and ThreadPoolExecutor.
 * 
 * @author Dmitry Nikolaenko
 *
 */
public class Main {
	
	private final BlockingThreadPoolExecutor blockingExecutor;
	private final ThreadPoolExecutor executor;
	
	public Main() {
		int corePoolSize = 3;
	    int maxPoolSize = 6;
	    int queueCapacity = 2;
		
		System.out.println("Creating BlockingThreadPoolExecutor and ThreadPoolExecutor");
		blockingExecutor = new BlockingThreadPoolExecutor(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(queueCapacity), new PriorityThreadFactory("workerThread"));
		executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS, 
				new LinkedBlockingQueue<Runnable>(queueCapacity), new PriorityThreadFactory("workerThread"));
	}
	
	
	public static void main(String[] args) {
		new Main().start();
	}
	
	public void start() {
		int rejectTaskCount = 0;
		
		System.out.println("ThreadPoolExecutor start:");
        for (int i = 0; i < 10; i++) {
            try { 
            	createAndInvokeTask("thread" + i, executor); 
            } catch (RejectedExecutionException ex) { 
            	System.out.println("task" + i + ": rejected"); 
            	rejectTaskCount++;
            } 
        }
        System.out.println("ThreadPoolExecutor done, total task rejections: " + rejectTaskCount);
         
        System.out.println("BlockingThreadPoolExecutor start:");
        rejectTaskCount = 0;
        for (int i = 0; i < 10; i++) {
            try { 
            	createAndInvokeTask("thread" + i, blockingExecutor); 
            } catch (RejectedExecutionException ex) { 
            	System.out.println("task" + i + ": rejected"); 
            	rejectTaskCount++;
            } 
        }
        System.out.println("BlockingThreadPoolExecutor done, total task rejections: " + rejectTaskCount);
	}
	
	private void createAndInvokeTask(final String name, ThreadPoolExecutor executor) {
		executor.execute(new TestThread(name));
	}
}
