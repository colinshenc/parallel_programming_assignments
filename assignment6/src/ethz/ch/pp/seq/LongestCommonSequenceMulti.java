package ethz.ch.pp.seq;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class LongestCommonSequenceMulti extends RecursiveTask<Sequence> {

	private static final long serialVersionUID = 4179716026313995745L;
//	private int numThreads; 	
	private int cutOff;
	private int [] input;
//	private Sequence result;
	private int start;
	private int end;
	public LongestCommonSequenceMulti(int[] input, int cutOff,int start,int end) {
//		this.numThreads=numThreads;
		this.cutOff = cutOff;
		this.input=input;
//		this.result=result;
		this.start=start;
		this.end=end;
	}
	public static Sequence longestCommonSequence(int[] input, int numThreads) {
		return longestCommonSequence(input, numThreads, 1000000);
	}
	public static Sequence longestCommonSequence(int[] input, int numThreads, int cutOff) {
		// TODO Implement
		LongestCommonSequenceMulti task = new LongestCommonSequenceMulti(input,cutOff,0,input.length-1);
		ForkJoinPool fjp = new ForkJoinPool(numThreads);
		return fjp.invoke(task);
		
	}

	@Override
	protected Sequence compute() {
		// TODO Implement		
		Sequence result;
		if (this.end-this.start+1<=cutOff) {
			result = LongestCommonSequence.longestCommonSequence(input,start,end-start+1);
		}else {
			int k=0;
			while(Math.exp(input[k+(start+end)/2]) == Math.exp(input[k+(start+end)/2+1])) {
				if (k+(start+end)/2+1==end) {
					continue;
				}
				k=k+1;
			}
			LongestCommonSequenceMulti task1=new LongestCommonSequenceMulti(input,cutOff,start,k+(start+end)/2);
			LongestCommonSequenceMulti task2=new LongestCommonSequenceMulti(input,cutOff,k+(start+end)/2+1,end);

			task1.fork();
			Sequence res2=task2.compute();
			Sequence res1=task1.join();
			if((res2.endIndex-res2.startIndex)>(res1.endIndex-res1.startIndex)) {
				result=res2;
			}else {
				result=res1;
			}
//			result=(res1.isGreaterThan(res2)) ? res1 : res2;
		}
		
		return result;		
	}
}
