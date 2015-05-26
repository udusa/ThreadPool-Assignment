package main;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import pool.PoolMagener;
import tasks.TaskPackage;

public class Feeder<V> extends Thread{
	
	private static ArrayList<Semaphore> mutexOrder = new ArrayList<>();
	private static int ID=0;
	
	private Semaphore mutex;
	private int id;
	private ArrayList<TaskPackage<V>> packageList;
	PoolMagener<V> pm;
	public Feeder(ArrayList<TaskPackage<V>> list,PoolMagener<V> pm){
		packageList=list;
		this.pm=pm;
		mutex=new Semaphore(1);
		id=ID;
		ID++;
		if(mutexOrder.size() > 1){
			mutexOrder.add(new Semaphore(0));
		}else{
			mutexOrder.add(new Semaphore(1));
		}
	}
	
	public void addSet(ArrayList<TaskPackage<V>> set){
		try {
			mutex.acquire();
		//	System.out.println(set);
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
		//	while(!packageList.isEmpty()){
				try {
					mutexOrder.get(id).acquire();
					TaskPackage<V> temp=null;
					if(!packageList.isEmpty())
						temp = packageList.get(0);
					if(temp!=null && pm.setPackage(temp)){
						mutex.acquire();
						packageList.remove(0);
						
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					mutex.release();
					mutexOrder.get((id+1)%mutexOrder.size()).release();;
				}
				/*
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
				*/
		//	}
		}
	}
	
	
	
	
}
