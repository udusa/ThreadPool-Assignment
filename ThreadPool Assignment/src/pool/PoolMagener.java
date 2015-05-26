package pool;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

import main.Result;
import tasks.TaskPackage;

public class PoolMagener<V> extends Thread {
	
	private BlockingQueue<TaskPackage<V>> packageQ;
	private ArrayList<Thread> poolThread;
	private ArrayList<Report<V>> readyList;
	private Result<V> result;
	private int packQSize;//,PThreadSize;
	
	private Semaphore pThreadMutex,readyMutex,packageMutex,reportMutex;
	
	public PoolMagener(Result<V> result,int p,int t) {
		this.result=result;
		packQSize=p;
		pThreadMutex = new Semaphore(1);
		readyMutex=new Semaphore(1);
		packageMutex=new Semaphore(1);
		reportMutex = new Semaphore(0);
		
		//PThreadSize=t;
		
		packageQ = new LinkedBlockingDeque<>(p);
		poolThread = new ArrayList<>(t);
		readyList = new ArrayList<>(2*p);
		
		for (int i = 0; i < t; i++) {
			PThread pt = new PThread();
			//poolThread.add(pt);
			pt.start();
		}
		
		ReportThread rt = new ReportThread();
		rt.start();
		
	}
	
	//RUN
	@Override
	public void run() {
		while(true){
			while(!packageQ.isEmpty()){
				if(!poolThread.isEmpty()){
					PThread t = (PThread)getThreadFromPool();
					TaskPackage<V> tp = getTaskPackage();
					if(t!=null){
						t.setTask(tp.getTask(), tp.getPackageId(), tp.getTaskId());
					}
				}
			}
		}
	}
	
	//GETTERS SETTERS
	public void setReport(Report<V> r) {
		try {
			readyMutex.acquire();
			readyList.add(r);
			//System.out.println(readyList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			reportMutex.release();
			readyMutex.release();
		}
	}
	
	public Report<V> getReport(){
		Report<V> rep=null;
		try {
			readyMutex.acquire();
			rep = readyList.remove(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			readyMutex.release();
		}
		return rep;
	}
	
	private boolean isFull() {
		return packageQ.size()==packQSize;
	}

	public boolean setPackage(TaskPackage<V> p) {
		//System.out.println(p);
		try {
			packageMutex.acquire();//down
			if(!isFull()){
				packageQ.put(p);
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}finally{
			packageMutex.release();//up
		}
		return false;
	}
	
	public TaskPackage<V> getTaskPackage(){
		TaskPackage<V> p=null;
		try {
			packageMutex.acquire();
			p=packageQ.poll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			packageMutex.release();
		}
		return p;
	}
	
	private void setThreadToPool(Thread t){
		try {
			pThreadMutex.acquire();
			poolThread.add(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			pThreadMutex.release();
		}
	}
	
	private Thread getThreadFromPool(){
		Thread t=null;
		try {
			pThreadMutex.acquire();
			t = poolThread.remove(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			pThreadMutex.release();
		}
		return t;
	}
	
	//CLASSES
	private class PThread extends Thread{
		
		private Object sync;
		Callable<V> task;
		boolean haveTask;
		private Report<V> r;
		
		public PThread() {
			haveTask=false;
			sync=new Object();
		}
		
		public void setTask(Callable<V> task,int pId,int tId){
			this.task=task;
			r=new Report<V>(null, pId, tId);
			//System.out.println(r);
			synchronized (sync) {
				haveTask=true;
				sync.notify();
			}
			
		}
		
		@Override
		public void run() {
			while(true){
				synchronized (sync) {
					setThreadToPool(this);
					while(!haveTask){
						try {
							sync.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					try {
						V result=task.call();
						//System.out.println("Thread:"+result);
						if(result!=null){
							r.setResult(result);
						//	System.out.println("Thread:"+r);
							setReport(r);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					haveTask=false;
				}
				//System.out.println(this);
			}
		}
		
	}
	
	private class ReportThread extends Thread{
		
		@Override
		public void run() {
			while(true){
				try {
					reportMutex.acquire();
					result.report(getReport());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	
}
