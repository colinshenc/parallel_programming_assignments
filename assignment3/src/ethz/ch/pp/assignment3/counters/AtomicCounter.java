package ethz.ch.pp.assignment3.counters;

import java.util.concurrent.atomic.AtomicInteger;

//TODO: implement
public class AtomicCounter implements Counter {
	public AtomicInteger count = new AtomicInteger(0);
    public void increment() {
    	count.addAndGet(1);
//		System.out.println("Thread "+Thread.currentThread().getId()+"!");

    }
    public int value() {
    	return count.get();
    }

}