package main.java.org.dmitrynikol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TestThreadPoolExecutor with two overridden methods: beforeExecute() and afterExecute(), 
 * allows you to control the logic when executed.
 * 
 * @author Dmitry Nikolaenko
 */
public class TestThreadPoolExecutor extends ThreadPoolExecutor {

	public TestThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		System.out.println("beforeExecute() logic");
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (t != null) {
			System.out.println("exception handler logic");
		}
		System.out.println("afterExecute() logic");
	}
}