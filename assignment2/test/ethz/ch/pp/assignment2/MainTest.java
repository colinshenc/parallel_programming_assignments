package ethz.ch.pp.assignment2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.assignment2.Main;
import ethz.ch.pp.assignment2.Main.ArraySplit;

public class MainTest {

	private final static Random rnd = new Random(42);
	
	@Test
	public void testTaskDForRandomSizes() {
		long start = System.nanoTime();

		final int N = 100000;
		final int T = 100;
		for (int t = 0; t < T; t++) {
//			int[] values = Main.generateRandomInput(rnd.nextInt(N) + 4);
			int[] values = Main.generateRandomInput(N);

//			int[] expected = Main.computePrimeFactors(values);
			int[] actual = Main.taskE(values, 4);//execution time lowest at thread=4, which is number of cores.
//			Assert.assertArrayEquals(
//					String.format("The results of sequential and parallel execution are not equal for \nInput: '%s'\n Expected: '%s'\n Actual: '%s'\n", Arrays.toString(values), Arrays.toString(expected), Arrays.toString(actual)), 
//					expected, 
//					actual);	
		}
		
		long end = System.nanoTime();
		System.out.format("Total time is: %d micro-seconds\n", (end - start));
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("Num cores "+cores);
	}
	
//	@Test
//	public void testTaskDForSmallSizes() {		
//		final int T = 100;
//		for (int t = 2; t < T; t++) {
//			int[] values = Main.generateRandomInput(t + 1);
//			int[] expected = Main.computePrimeFactors(values);
//			int[] actual = Main.taskE(values, 3);
//			Assert.assertArrayEquals(
//					String.format("The results of sequential and parallel execution are not equal for \nInput: '%s'\n Expected: '%s'\n Actual: '%s'\n", Arrays.toString(values), Arrays.toString(expected), Arrays.toString(actual)), 
//					expected, 
//					actual);	
//		}		
//	}
//
//	@Test
//	public void testDataPartitionForSmallSizes() {
//		final int T = 100;
//		for (int numPartitions = 1; numPartitions < 5; numPartitions++) {
//			for (int t = numPartitions; t < T; t++) {			
//				Main.ArraySplit[] partitions = Main.PartitionData(t, numPartitions);
//				Arrays.sort(partitions, new Comparator<Main.ArraySplit>() {
//
//					@Override
//					public int compare(ArraySplit s1, ArraySplit s2) {
//						return s1.startIndex - s2.startIndex;
//					}
//				});
//
//				int index = 0;
//				for (Main.ArraySplit p : partitions) {
//					Assert.assertEquals(String.format("Partition for size %d and number of threads %d is incorrect!", t, numPartitions), p.startIndex, index);
//					index += p.length;
//				}
//				Assert.assertEquals(String.format("Partition for size %d and number of threads %d is incorrect!", t, numPartitions), t, index);			
//
//			}
//		}
//	}

}
