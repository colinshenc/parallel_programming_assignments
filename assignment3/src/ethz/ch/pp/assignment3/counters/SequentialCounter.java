package ethz.ch.pp.assignment3.counters;

//TODO: implement
public class SequentialCounter implements Counter {
	public int count = 0;
	
	public void increment() {
		count = count+ 1;
		System.out.println("Thread "+Thread.currentThread().getId()+"!");
 
	}
	public int value() {
		return count;
	}
}