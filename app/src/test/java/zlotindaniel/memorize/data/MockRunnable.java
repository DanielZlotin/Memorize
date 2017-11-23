package zlotindaniel.memorize.data;

public class MockRunnable implements Runnable {
	public int calls;

	@Override
	public void run() {
		calls++;
	}
}
