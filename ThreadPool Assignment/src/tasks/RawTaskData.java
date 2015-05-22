package tasks;

public class RawTaskData {
	
	private long packageID;
	private long taskId;
	private long amount;
	
	public RawTaskData(long packageID, long taskId, long amount) {
		super();
		this.packageID = packageID;
		this.taskId = taskId;
		this.amount = amount;
	}

	public long getPackageID() {
		return packageID;
	}

	public long getTaskId() {
		return taskId;
	}

	public long getAmount() {
		return amount;
	}
	
	
	
}
