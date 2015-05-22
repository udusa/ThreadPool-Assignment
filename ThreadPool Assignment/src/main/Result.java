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
	
	private int isContains(Report<V> r){
		int index = -1;
		for (int i = 0; i < results.size(); i++) {
			if(results.get(i).get(0).getPackageId()==r.getPackageId())
				return i;
		}
		return index;
	}
	
	private void add(Report<V> r){
		boolean added = false;
		for (int i = 0; i < results.size(); i++) {
			ArrayList<Report<V>> list = results.get(i);
			if(list.get(0).getPackageId()==r.getPackageId()){
				list.add(r);
				added=true;
			}
		}
		if(!added){
			ArrayList<Report<V>> newArray = new ArrayList<>();
			newArray.add(r);
			results.add(newArray);
		}
	}
	
	public Report<V> getResult(){
		try {
			mutex.acquire();
		//	if(!results.isEmpty()){
		//		return results.get(0);
		//	}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mutex.release();
		}
		return null;
		
	}
	
	public boolean report(Report<V> r){
		try {
			mutex.acquire();
		//	results.add(r);
		//	System.out.println("RESULT:"+r);
			add(r);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mutex.release();
		}
		return false;
	}

}
