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
		int i = 0;
        System.out.println(name + ": is running");
        
        while (i++ < 50)
        {
            try { 
            	Thread.sleep(10); 
            } catch (InterruptedException ex) { 
            	System.out.println(name + ": intrrupted");
            	break;
            }
        }
        
		/*try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}*/
	}
}
