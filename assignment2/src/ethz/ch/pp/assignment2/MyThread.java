package ethz.ch.pp.assignment2;

public class MyThread extends Thread{
	public void run () {
		System.out.println("Hello Thread "+this.getId()+"!"); //print current thread
	}
}


