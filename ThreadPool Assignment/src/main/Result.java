package main;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import pool.Report;

public class Result<V> {
	
	ArrayList<Report<V>> results;
	Semaphore mutex;
	
	
	public Result(){
		results=new ArrayList<>();
	}
	
	public Report<V> getResult(){
		try {
			mutex.acquire();
			if(!results.isEmpty()){
				return results.get(0);
			}
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
			results.add(r);
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
