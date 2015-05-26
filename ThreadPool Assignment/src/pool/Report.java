package pool;

public class Report<V> {
	private V result;
	private int packageId,taskId;
	
	public Report(V result,int packageId,int taskId) {
		this.result=result;
		this.packageId=packageId;
		this.taskId=taskId;
	}

	public V getResult() {
		return result;
	}

	public int getPackageId() {
		return packageId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setResult(V result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "result:"+result+",pID:"+packageId+",tID:"+taskId;
	}
	
//	@Override
//	public String toString() {
//		return "pID:"+packageId+",tID:"+taskId;
//	}
}
