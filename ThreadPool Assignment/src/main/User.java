package main;

import java.util.ArrayList;

import pool.PoolMagener;
import tasks.BasicTask11;
import tasks.RawTaskData;
import tasks.TaskPackage;


public class User {

	public static void main(String[] args) {
		
		//test();
		
		int arr11[] = {13};
		int m=5;
		submit(arr11, m);
	
		
	}
	
	public static void submit(int arr11[],int m){
		
		ArrayList<RawTaskData> follow = new ArrayList<>();
		ArrayList<TaskPackage<Double>> packages = new ArrayList<>();
		
		long pId = 0;
		long mtId = TaskPackage.MULTIPLICATION_TASK;
		
		for (int i = 0; i < arr11.length; i++) {
			int n = arr11[i]+1;
			int amount = n/m;
			if(n%m!=0){
				amount++;
			}
			for (int j = 1; j < arr11[i]+1; j+=m) {
				if(j+m < n){
					//System.out.println("["+j+","+(j+m)+"]");
					packages.add(new TaskPackage<>(pId, mtId, amount, new BasicTask11(j, j+m)));
				}else{
					packages.add(new TaskPackage<>(pId, mtId, amount, new BasicTask11(j, n)));
					//System.out.println("["+j+","+(n)+"]");
				}
			}
		}
		Result<Double> result = new Result<>();
		PoolMagener<Double> pm = new PoolMagener<>(result, 10, 10);
		Feeder<Double> f =new Feeder<>(packages, pm);
		pm.start();
		f.start();
		
		
	}
	
	public static void test(){
		BasicTask11 t= new BasicTask11(1, 5);
		TaskPackage<Double> tp = new TaskPackage(1,TaskPackage.MULTIPLICATION_TASK,34,t);
		
		ArrayList<TaskPackage<Double>> list = new ArrayList<>();
		list.add(tp);
		
		Result<Double> res = new Result<>();
		PoolMagener<Double> pm = new PoolMagener<>(res, 5, 5);
		Feeder<Double> f = new Feeder<>(list, pm);
		pm.start();
		f.start();
	}

}
