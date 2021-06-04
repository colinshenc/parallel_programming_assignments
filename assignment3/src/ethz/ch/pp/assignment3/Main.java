package ethz.ch.pp.assignment3;

import java.util.ArrayList;
import java.util.List;

import ethz.ch.pp.assignment3.counters.AtomicCounter;
import ethz.ch.pp.assignment3.counters.Counter;
import ethz.ch.pp.assignment3.counters.SequentialCounter;
import ethz.ch.pp.assignment3.counters.SynchronizedCounter;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory.ThreadCounterType;


public class Main {

	public static void count(final Counter counter, int numThreads, ThreadCounterType type, int numInterations) {
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(ThreadCounterFactory.make(type, counter, i, numThreads, numInterations)));
		}

		for (int i = 0; i < numThreads; i++) {
			threads.get(i).start();
		}
		
		for (int i = 0; i < numThreads; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
//		taskASequential();
//		taskAParallel();
//		taskB();
//		taskD();
//		taskE();
//		taskF();
		taskG();

	}
	
	public static void taskASequential(){
		Counter counter = new SequentialCounter();
		count(counter, 1, ThreadCounterType.NATIVE, 100000);
		System.out.println("TaskASequentialCounter: " + counter.value());
	}

	public static void taskAParallel(){
		Counter counter = new SequentialCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("TaskAParallelCounter: " + counter.value());
	}
	
	public static void taskB(){
		Counter counter = new SynchronizedCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100);
		System.out.println("TaskBCounter: " + counter.value());
	}
	
	public static void taskD(){
		Counter counter = new SynchronizedCounter(); //TODO: which type of counter can we use here? Both Sequential and Synchronzied seems to be ok.
		count(counter, 4, ThreadCounterType.FAIR, 100);
		System.out.println("TaskDCounter: " + counter.value());
	}
	
	public static void taskE(){
		Counter counter = new AtomicCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100);
		System.out.println("TaskECounter: " + counter.value());
	}
	public static void taskF(){
		Counter counter = new SynchronizedCounter();
		long start = System.nanoTime();
//		count(counter, 4, ThreadCounterType.NATIVE, 100);
		for (int i=0;i<10;i++) {
			count(counter, 4, ThreadCounterType.NATIVE, 100);
		}
		long duration = System.nanoTime()-start;
		System.out.format("Synchronized Counter Runtime: %d nano-seconds\n", duration/10);
		counter = new AtomicCounter();

		start = System.nanoTime();
//		count(counter, 4, ThreadCounterType.NATIVE, 100);
		for (int i=0;i<10;i++) {
			count(counter, 4, ThreadCounterType.NATIVE, 100);
		}
		duration = System.nanoTime()-start;
		System.out.format("Atmoic Counter Runtime: %d nano-seconds\n", duration/10);
	
	}
	public static void taskG() {
		Counter counter = new AtomicCounter();
		Thread progressThread = new Thread() {
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
//					System.out.println("\naaa");
					System.out.format("\r%d", counter.value());
					System.out.flush();
				}
			}
		};
		progressThread.start();
		count(counter, 4, ThreadCounterType.NATIVE, 1000000);
		progressThread.interrupt();
		
		try {
			progressThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("\nTaskGCounter: " + counter.value());

		
	}
	

}
