package main;

import java.util.ArrayList;
import java.util.List;

import pool.PoolMagener;
import pool.Report;
import tasks.BasicTask11;
import tasks.MainTask11;
import tasks.RawTaskData;
import tasks.TaskPackage;


public class User {

	public static void main(String[] args) {
		
		//test();
		
		int arr11[] = {2};
		int m=2;
		test(arr11);
		submit(arr11, m);
	
		
	}
	
	public static void submit(int arr11[],int m){
		
		ArrayList<RawTaskData> follow = new ArrayList<>();
		ArrayList<TaskPackage<Double>> packages = new ArrayList<>();
		
		long pId = 0;
		long mtId = TaskPackage.MULTIPLICATION_TASK;
		
		for (int i = 0; i < arr11.length; i++) {
			int n = arr11[i];
			int amount = n/m;
			if(n%m!=0){
				amount++;
			}
			for (int j = 1; j < arr11[i]+1; j+=m) {
				if(j+m < n+1){
					//System.out.println("["+j+","+(j+m)+"]");
					packages.add(new TaskPackage<>(pId, mtId, amount, new BasicTask11(j, j+m)));
				}else{
					packages.add(new TaskPackage<>(pId, mtId, amount, new BasicTask11(j, n+1)));
					//System.out.println("["+j+","+(n)+"]");
				}
			}
			follow.add(new RawTaskData(pId, mtId, amount));
			//System.out.println(new RawTaskData(pId, mtId, amount));
			pId++;
		}
		Result<Double> result = new Result<>();
		PoolMagener<Double> pm = new PoolMagener<>(result, 10, 10);
		Feeder<Double> f =new Feeder<>(packages, pm);
		pm.start();
		f.start();
		
		while(true){
			int i=0;
			while(!follow.isEmpty()){
				RawTaskData data = follow.get(i);
				ArrayList<Report<Double>> ans = result.getResultSet(data.getPackageID(), data.getTaskId(), data.getAmount());
				//System.out.println(ans);
				if(ans!=null){
					//follow.remove(i);
					if(data.getAmount()==1){
						System.out.println(ans.get(0));
					}else{
						ArrayList<TaskPackage<Double>> mainTasks = new ArrayList<>();
						int n = ans.size();
						int amount = ans.size()/m;
						if(ans.size()%m!=0){
							amount++;
						}
						for (int j = 0; j < n; j+=m) {
							if(j+m < n){
								List<Report<Double>> subList = ans.subList(j, j+m);
								mainTasks.add(new TaskPackage<>(pId, mtId, amount, new MainTask11(subList)));
							}else{
								List<Report<Double>> subList = ans.subList(j, n);
								mainTasks.add(new TaskPackage<>(pId, mtId, amount, new MainTask11(subList)));
							}
						}
						follow.add(new RawTaskData(pId, mtId, amount));
						pId++;
						f.addSet(mainTasks);
					//	System.out.println(mainTasks);
						
					//	System.out.println(new RawTaskData(pId, mtId, amount));
					}
				}
				i=(i+1)%follow.size();
				/*
				synchronized (result) {
					try {
						result.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				*/
			}
		}
	}
	
	public static void test(int arr11[]){
		
		for (int i = 0; i < arr11.length; i++) {
			BasicTask11 t = new BasicTask11(1, arr11[i]+1);
			try {
				System.out.println("TEST : "+t.call());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
