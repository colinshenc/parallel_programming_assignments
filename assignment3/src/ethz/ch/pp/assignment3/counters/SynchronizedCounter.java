package ethz.ch.pp.assignment3.counters;

//TODO: implement
public class SynchronizedCounter implements Counter {
	public int count = 0;
	
	public synchronized void increment() {
		count = count+ 1;
//		System.out.println("Thread "+Thread.currentThread().getId()+"!");

	 }
	public synchronized int value() {
		return count;
	}
}