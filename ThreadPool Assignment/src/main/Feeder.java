package main;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import pool.PoolMagener;
import tasks.TaskPackage;

public class Feeder<V> extends Thread{
	
	private Semaphore mutex;
	private ArrayList<TaskPackage<V>> packageList;
	PoolMagener<V> pm;
	public Feeder(ArrayList<TaskPackage<V>> list,PoolMagener<V> pm){
		packageList=list;
		this.pm=pm;
		mutex=new Semaphore(1);
	}
	
	public void addSet(ArrayList<TaskPackage<V>> set){
		try {
			mutex.acquire();
			packageList.addAll(set);	
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}finally{
			mutex.release();
		}
		
	}
	
	
	@Override
	public void run() {
		while(true){
			if(!packageList.isEmpty()){
				TaskPackage<V> temp = packageList.get(0);
				if(pm.setPackage(temp)){
					try {
						mutex.acquire();
						packageList.remove(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						mutex.release();
					}
				}
			}
		}
	}
	
	
	
	
}
