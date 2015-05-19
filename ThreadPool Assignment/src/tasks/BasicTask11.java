package tasks;
import java.util.concurrent.Callable;


public class BasicTask11 implements Callable<Double>{
	
	private int fromIndex,toIndex;
	 
	public BasicTask11(int fromIndex,int toIndex){
		this.fromIndex=fromIndex;
		this.toIndex=toIndex;
	}
	
	@Override
	public Double call() throws Exception {
		double ans = 1;
		int coefficient = 1;
		for (int i = fromIndex; i < toIndex; i++) {
			if(i%2!=0)coefficient=-1;
			else coefficient=1;
			
			double temp = coefficient*(1.0/(2*i+1));
			ans *= temp;
			
		}
		return ans;
	}
	
	
	
}
