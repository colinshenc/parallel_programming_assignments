package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountSeqDivideAndConquer {

	private int[] input;	
	Workload.Type workloadType;
	
	public SearchAndCountSeqDivideAndConquer(int[] input, Workload.Type wt) {
		this.input = input;		
		this.workloadType = wt;
	}
	
	public static int countNoAppearances(int[] input, Workload.Type wt) {
		//TODO implement
		SearchAndCountSeqDivideAndConquer counter = new SearchAndCountSeqDivideAndConquer(input, wt);
	
		return counter.count(0,input.length-1,0);	}
	
	private int count(int startIndex, int endIndex, int count) {
		
		if (startIndex==endIndex) {
			if (Workload.doWork(input[startIndex], workloadType))
				count= count+1;
				return count;
		}

		return count(startIndex, (startIndex+endIndex)/2,count)+count((startIndex+endIndex)/2+1,endIndex,count);

	}
	
	
}