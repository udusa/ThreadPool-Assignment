package main;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import pool.Report;

public class Result<V> {
	
	//ArrayList<Report<V>> results;
	ArrayList<ArrayList<Report<V>>> results;
	Semaphore mutex;
	
	
	public Result(){
		results=new ArrayList<>();
		mutex=new Semaphore(1);
	}
	
	private int isContains(long pId,long amount){
		int index = -1;
		for (int i = 0; i < results.size(); i++) {
			if(results.get(i).get(0).getPackageId()==pId && results.get(i).size()==amount)
				return i;
		}
		return index;
	}
	
	private void add(Report<V> r){
		//System.out.println(r);
		boolean added = false;
		for (int i = 0; i < results.size(); i++) {
			ArrayList<Report<V>> list = results.get(i);
			if(list.get(0).getPackageId()==r.getPackageId()){
				list.add(r);
				added=true;
				//System.out.println("RESULT:"+r);
			}
		}
		if(!added){
			ArrayList<Report<V>> newArray = new ArrayList<>();
			newArray.add(r);
			results.add(newArray);
			//System.out.println("RESULT:"+r);
		}
		//System.out.println(results);
	}
	
	public ArrayList<Report<V>> getResultSet(long pId,long tId,long amount){
		int index = isContains(pId, amount);
		if(index==-1)return null;
		try {
			mutex.acquire();
			return results.remove(index);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			mutex.release();
		}
		return null;
		
	}
	
	public boolean report(Report<V> r){
		try {
			mutex.acquire();
			//System.out.println("RESULT:"+r);
			add(r);
			synchronized (this) {
				this.notifyAll();
			}
			
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			mutex.release();
		}
		return false;
	}

}
