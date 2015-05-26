package tasks;
import java.util.concurrent.Callable;


public class TaskPackage<V> {
	public final static int MULTIPLICATION_TASK=1,SUMMATION_TASK=2;
	protected long amount;
	protected int taskId,packageId;
	protected Callable<V> task;
	
	
	public TaskPackage(int packageId,int taskId,long amount,Callable<V> task){
		this.taskId=taskId;
		this.task=task;
		this.packageId=packageId;
		this.amount = amount;
	}
	
	public Callable<V> getTask(){
		return task;
	}
	
	
	public int getTaskId(){
		return taskId;
	}

	public int getPackageId() {
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
