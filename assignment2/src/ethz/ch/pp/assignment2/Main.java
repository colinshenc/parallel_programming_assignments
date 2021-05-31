package ethz.ch.pp.assignment2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
 		
		// TODO: adjust appropriately for the required experiments
//		taskA();
		int[] input1 = generateRandomInput(1000);
//		int[] input2 = generateRandomInput(10000);
//		int[] input3 = generateRandomInput(100000);
//		int[] input4 = generateRandomInput(1000000);
//		
//		// Sequential version
//		taskB(input1);
//		taskB(input2);
//		taskB(input3);
//		taskB(input4);
//		
//		// Parallel version
		taskE(input1, 4);
//		taskE(input2, 4);
//		taskE(input3, 4);
//		taskE(input4, 4);
//		
//		
//		long threadOverhead = taskC();
//		System.out.format("Thread overhead on current system is: %d nano-seconds\n", threadOverhead);		
	}

	private final static Random rnd = new Random(42);

	public static int[] generateRandomInput() {
		return generateRandomInput(rnd.nextInt(10000) + 1);
	}
	
	public static int[] generateRandomInput(int length) {	
		int[] values = new int[length];		
		for (int i = 0; i < values.length; i++) {
			values[i] = rnd.nextInt(99) + 1;				
		}		
		return values;
	}
	
	public static int[] computePrimeFactors(int[] values) {		
		int[] factors = new int[values.length];	
		for (int i = 0; i < values.length; i++) {
			factors[i] = numPrimeFactors(values[i]);
		}		
		return factors;
	}
	
	public static int numPrimeFactors(int number) {
		int primeFactors = 0;
		int n = number;		
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				primeFactors++;
				n /= i;
			}
		}
		return primeFactors;
	}
	
	public static class ArraySplit {
		public final int startIndex;
		public final int length;
		
		ArraySplit(int startIndex, int length) {
			this.startIndex = startIndex;
			this.length = length;
		}
	}
	public static void calculatePartitions(int length, int currlength, int numP, int count, ArraySplit[] splits) {
		if (numP==0) {
			return;
		}
		int k = (currlength + numP -1)/numP; //round up integer divisions.
		splits[count]=new ArraySplit(length-currlength,k);
		calculatePartitions(length,currlength-k,numP-1,count+1,splits);
	}
	// TaskD
	public static ArraySplit[] PartitionData(int length, int numPartitions) {//Program assumes data and partition size must be positive, and data size must be greater than partition size.
		assert (length > 0) && (numPartitions > 0);
		assert length >= numPartitions;
		ArraySplit[] splits = new ArraySplit[numPartitions];
		calculatePartitions(length,length,numPartitions,0,splits);
//		int k = (length + numPartitions -1)/numPartitions; //round up integer divisions.
//		int startI = 0;
//		for (int i=0;i<numPartitions;i++) {
//			if(k*(i+1)>length) {
//				splits[i]=new ArraySplit(startI,k-1);
//			}else {
//			splits[i]=new ArraySplit(startI,k);
//			startI = startI+k;
//			}
//		}
		
		return splits;
		//TODO: implement
//		throw new UnsupportedOperationException();
		
	}
	
	public static void taskA() {
		//TODO: implement
		Thread t = new MyThread();
		
		t.start();
		try {
			t.join(); //ensure main thread finishes after thread t.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Main Thread ID "+Thread.currentThread().getId()+"!");

//		throw new UnsupportedOperationException();
	}
	public static int[] taskBmainThread(int[] values) { //Sequential execution
		return computePrimeFactors(values);
	}
	
	public static class PrimeFactorsThread extends Thread { // A static nested class may be instantiated without instantiating its outer class.
		// thread local data
		private int[] result;
		private int[] values;

		public PrimeFactorsThread(int[] values) {
//			this.values = values;
			assert result==null;

		}

		@Override
		public void run() {
			result = computePrimeFactors(values);
		}

		public int[] getResult() {
//			assert result != null;
			return result;
		}
	}

	public static class dummyThread extends Thread {
		@Override
		public void run() {
			dummy();
		}
		
	}

	public static int[] taskBsideThread(int[] values) {//This is sequential.
		PrimeFactorsThread t = new PrimeFactorsThread(values);
		
		t.start();
		try {
			t.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		};
		return t.getResult();
	
	}

	public static int[] taskB(final int[] values) { //Static methods are the methods in Java that can be called without creating an object of class.They are designed with the aim to be shared among all objects created from the same class.
		//TODO: implement
//		long startTime = System.nanoTime();
		final int N = 100;
		int[] res=null;
		long startTime = System.nanoTime();

		for (int i=1;i<=N;i++) {
			res = taskBmainThread(values);

		}
		
		long endMaintime = System.nanoTime();
		long elapsedNsmain = (endMaintime - startTime)/N;
		System.out.format("B.Main Thread time is: %d nano-seconds\n", elapsedNsmain);
		
		startTime = System.nanoTime();
		for (int i=1;i<=N;i++) {
			res = taskBsideThread(values);
			
		}
		
		long endSidetime = System.nanoTime();
		long elapsedNsside = (endSidetime - startTime)/N;
		System.out.format("B.Side Thread time is: %d nano-seconds\n", elapsedNsside);

		return res;
		
	}
	public static void taskCsideThread() {
		dummyThread t = new dummyThread();
		
		t.start();
		try {
			t.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		};
		
	
	}
//	 Returns overhead of creating thread in nano-seconds
	public static void dummy() {
		 
	}
	public static long taskC() {		
		//TODO: implement
		//throw new UnsupportedOperationException();
		final int N = 100;
		Object res=null;
		long startTime = System.nanoTime();

		for (int i=1;i<=N;i++) {
			dummy();

		}
		
		long endMaintime = System.nanoTime();
		long elapsedNsmain = (endMaintime - startTime)/N;
		System.out.format("C.Main Thread time is: %d nano-seconds\n", elapsedNsmain);
		
		startTime = System.nanoTime();
		for (int i=1;i<=N;i++) {
			taskCsideThread();
			
		}
		
		long endSidetime = System.nanoTime();
		long elapsedNsside = (endSidetime - startTime)/N;
		System.out.format("C.Side Thread time is (Overhead): %d nano-seconds\n", elapsedNsside);

		return elapsedNsside;
	}
//	public static class taskEparThread extends Thread{
//		
//		private int result;
//		private int value;
//		public taskEparThread(int value) {
//			this.value = value;
////			assert result==null;
//
//		} 
//		public void run() {
//			
//			result= numPrimeFactors(value)
//		}
//	}
	public static Thread prepThreadandRun(int startI, int length, int[] values, int[] result) {
		Thread t = new Thread() {
			public void run() {
				for (int i=startI; i<startI+length;i++) {
					result[i]=numPrimeFactors(values[i]); 
				}
			}
		};
//		Thread t = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				for (int i = startI; i < startI + length; i++) {
//					result[i] = numPrimeFactors(values[i]);
//				}
//			}
//		});
		return t;
	}
	public static int[] taskE(final int[] values, final int numThreads) {
		//TODO: implement
//		throw new UnsupportedOperationException();
		ArraySplit[] splits = PartitionData(values.length,numThreads);
//		for(int i=0;i<splits.length;i++) {
//			System.out.println("start"+ splits[i].startIndex+"length"+splits[i].length);
//		}
		
//		System.out.println(Arrays.toString(values));
		int[] result = new int[values.length];
//		int count = 0;
//		for (int i=0;i<numThreads;i++) {
//	        int[] slice = Arrays.copyOfRange(
//                        // Source
//                        values,
//                        // The Start index
//                        splits[i].startIndex,
//                        // The end index
//                        splits[i].startIndex+splits[i].length);
//			System.out.println("slice "+ Arrays.toString(slice));
//			List<Thread> threads = new ArrayList<Thread>();
		
			
//			int[]temp= taskBsideThread(slice);
//			for (int j=0;j<temp.length;j++) {
//				try {
//				result[count]=temp[j];
//				}catch (java.lang.ArrayIndexOutOfBoundsException e){
//					System.out.println("count"+ count);
//					System.out.println("temp length"+ temp.length);
//					System.out.println("j"+ j);
//
//				};
//				count = count +1;
//			}
//		}
//		assert count==values.length;
//		System.out.println("result 0 "+ result[0]);
//		System.out.println("result 1 "+ result[1]);
//
//		System.out.println("E."+ count);
		List<Thread> threads=new ArrayList<Thread>();
		
		for (ArraySplit split:splits) {
			threads.add(prepThreadandRun(split.startIndex,split.length,values,result));
		}
		for (Thread t:threads) {
			t.start();
		}
		for(Thread t:threads) {
			
			try {
				t.join();
			}catch (InterruptedException e){
				e.printStackTrace();
			};
		}//Run threads in parallel, if start() and join() inside one loop then main thread is blocked until each thread is terminated.
		return result;
	}


}
