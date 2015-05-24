package tasks;
import java.util.concurrent.Callable;


public class TaskPackage<V> {
	public final static int MULTIPLICATION_TASK=1,SUMMATION_TASK=2;
	protected long taskId,packageId,amount;
	protected Callable<V> task;
	
	
	public TaskPackage(long packageId,long taskId,long amount,Callable<V> task){
		this.taskId=taskId;
		this.task=task;
		this.packageId=packageId;
		this.amount = amount;
	}
	
	public Callable<V> getTask(){
		return task;
	}
	
	
	public long getTaskId(){
		return taskId;
	}

	public long getPackageId() {
		return packageId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TP[tId=" + taskId + ", pId=" + packageId
				+ ", amt=" + amount + ", task=" + task + "]";
	}
}
