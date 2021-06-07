package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountThreadDivideAndConquer{
	static private int[] input;
	static Workload.Type workloadType;
	static int numThreads;
	static int cutOff;
	public static class taskThread extends Thread{
		SearchAndCountThreadDivideAndConquer task;
		int startIndex;
		int endIndex;
		int result;
		public taskThread(SearchAndCountThreadDivideAndConquer task,int startIndex,int endIndex) {
			this.task=task;
			this.startIndex=startIndex;
			this.endIndex=endIndex;
		}
		public void run() {
			result=count(startIndex, endIndex,0);
		}
	}
	public SearchAndCountThreadDivideAndConquer(int[] input, Workload.Type wt, int numThreads, int cutOff) {
		SearchAndCountThreadDivideAndConquer.input = input;		
		SearchAndCountThreadDivideAndConquer.workloadType = wt;
		SearchAndCountThreadDivideAndConquer.numThreads=numThreads;
		SearchAndCountThreadDivideAndConquer.cutOff=cutOff;
	}
	
	public static int countNoAppearances() {
		//TODO implement
//		SearchAndCountThreadDivideAndConquer counter = new SearchAndCountThreadDivideAndConquer(input,wt,cutOff,numThreads);
		int result = count(0, input.length-1,0); 
		return result;
	}
	public static int count(int startIndex, int endIndex, int id) {
		if (startIndex+cutOff<=endIndex) {
			int count = 0;
			for(int i=startIndex; i<=endIndex;i++) {
				if (Workload.doWork(input[startIndex+i], workloadType)) {
					count= count+1;
			}
		}
			return count;
			
		}
		taskThread t1 = new taskThread(new SearchAndCountThreadDivideAndConquer(input,workloadType, numThreads,cutOff),startIndex, (startIndex+endIndex)/2);
		taskThread t2 = new taskThread(new SearchAndCountThreadDivideAndConquer(input, workloadType, numThreads,cutOff),(startIndex+endIndex)/2+1, endIndex);
		
		if(id<numThreads-1) {
			t2.start();
			int t1Res=t1.task.count(startIndex, (startIndex+endIndex)/2,2*id+2);
			
			try {
				t2.join();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return t1Res+t2.result;
		}else {
			return t1.task.count(startIndex, (startIndex+endIndex)/2, 2*id+1)+t2.task.count((startIndex+endIndex)/2+1, endIndex, 2*id+2);
		}
		
	}
	
}