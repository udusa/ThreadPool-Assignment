package tasks;
import java.util.concurrent.Callable;


public abstract class TaskPackage<V> {
	public final static int MULTIPLICATION_TASK=1,SUMMATION_TASK=2;
	protected long taskId,packageId;
	protected double result;
	protected Callable<V> task;
	
	
	public TaskPackage(long packageId,long taskId,Callable<V> task){
		this.taskId=taskId;
		this.task=task;
		this.packageId=packageId;
	}
	
	public Callable<V> getTask(){
		return task;
	}
	
	public double getResult(){
		return result;
	}
	
	public long getTaskId(){
		return taskId;
	}

	public long getPackageId() {
		return packageId;
	}
	
}
