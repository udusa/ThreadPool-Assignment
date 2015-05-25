package tasks;

public class RawTaskData {
	
	private int packageID;
	private int taskId;
	private long amount;
	
	public RawTaskData(int packageID, int taskId, long amount) {
		super();
		this.packageID = packageID;
		this.taskId = taskId;
		this.amount = amount;
	}

	public int getPackageID() {
		return packageID;
	}

	public int getTaskId() {
		return taskId;
	}

	public long getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "RawTaskData [packageID=" + packageID + ", taskId=" + taskId
				+ ", amount=" + amount + "]";
	}
	
	
	
}
