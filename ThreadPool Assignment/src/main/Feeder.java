package main;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import pool.PoolMagener;

public class Feeder extends Thread{
	
	private Semaphore mutex;
	private ArrayList<Package> packageList;
	PoolMagener pm;
	public Feeder(ArrayList<Package> list,PoolMagener pm){
		packageList=list;
		this.pm=pm;
		mutex=new Semaphore(1);
	}
	
	public void addSet(ArrayList<Package> set){
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
			if(!pm.full() && !packageList.isEmpty()){
				Package temp = packageList.get(0);
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
