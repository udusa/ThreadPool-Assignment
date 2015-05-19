package pool;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

public class PoolMagener<V> extends Thread {
	
	BlockingQueue<Package> packageQ;
	ArrayList<Thread> poolThread;
	
	Semaphore pThreadMutex;
	
	public PoolMagener(int p,int t) {
		packageQ = new LinkedBlockingDeque<>(p);
		poolThread = new ArrayList<>(t);
		for (int i = 0; i < t; i++) {
			poolThread.add(new pThread());
		}
		
		pThreadMutex = new Semaphore(1);
	}
	
	
	@Override
	public void run() {
		
	}

	public boolean full() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setPackage(Package temp) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void setThreadToPool(Thread t){
		try {
			pThreadMutex.acquire();
			poolThread.add(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			pThreadMutex.release();
		}
		return t;
	}
	private class pThread extends Thread{
		
		private Object sync;
		Callable<V> task;
		boolean haveTask;
		public pThread() {
			haveTask=false;
			sync=new Object();
		}
		
		public void setTask(Callable<V> task){
			this.task=task;
			sync.notify();
		}
		
		@Override
		public void run() {
			while(true){
				synchronized (sync) {
					while(!haveTask){
						try {
							sync.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					try {
						V result=task.call();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
