package main.java.org.dmitrynikol;

public class TestThread implements Runnable {

	private String name = null;

	public TestThread(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("Executing thread: " + name);
	}
}