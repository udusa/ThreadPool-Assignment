package pool;

public class Report<V> {
	private V result;
	private long packageId,taskId;
	
	public Report(V result,long packageId,long taskId) {
		this.result=result;
		this.packageId=packageId;
		this.taskId=taskId;
	}

	public V getResult() {
		return result;
	}

	public long getPackageId() {
		return packageId;
	}

	public long getTaskId() {
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
