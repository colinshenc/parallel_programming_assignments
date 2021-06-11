package ethz.ch.pp.mergeSort;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;
import java.util.Arrays;

import ethz.ch.pp.util.ArrayUtils;


public class MergeSortMulti extends RecursiveAction {//No return value
	private int[] input;
	private int start;
	private int end;
	private int[] res;
	public MergeSortMulti(int[] input, int start, int end) {
		this.input = input;
		this.start = start;
		this.end =end;
		res = new int[end-start+1];
	}
	private static final long serialVersionUID = 1531647254971804196L;

	//TODO: implement using ForkJoinPool
	// You should change this class to extend from RecursiveTask or RecursiveAction
	public static int[] sort(int[] input, int numThreads) {		
		MergeSortMulti task = new MergeSortMulti(input,0 , input.length-1);
		ForkJoinPool fjp = new ForkJoinPool(numThreads);
		fjp.invoke(task);
		return task.res;
	}
	protected void compute() {
		if (end-start<=1) {
			 simpleSort();
			 
		}else {
		MergeSortMulti task1 = new MergeSortMulti(input, start, (end+start)/2);//[start,end], both sides included
		MergeSortMulti task2 = new MergeSortMulti(input, (end+start)/2+1, end);
		task2.fork();
		task1.compute();
//		int[] res1=task1.res;
		task2.join();
//		int[] res2=task2.res;
		ArrayUtils.merge(task1.res,task2.res,res); 
//		System.out.println(Arrays.toString(res1));
//		System.out.println(Arrays.toString(res2));
//		System.out.println(Arrays.toString(res1));


		}
	}
  
	private void simpleSort() {
	  if (end-start<1) {
		  res[0]=input[start];
		  
	  } else{
		  if (input[start]>input[end]) {
			  res[1] = input[start];
			  res[0] = input[end];
		  }else {
			  res[0] = input[start];
			  res[1] = input[end];
		  }
//		  return res;
	  }
	  
  }
  
  
}
