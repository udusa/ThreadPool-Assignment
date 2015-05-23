package main;

import java.util.ArrayList;
import java.util.List;

import pool.PoolMagener;
import pool.Report;
import tasks.BasicTask11;
import tasks.BasicTask12mul;
import tasks.MainTaskMul;
import tasks.MainTaskSum;
import tasks.RawTaskData;
import tasks.TaskPackage;


public class User {

	public static void main(String[] args) {
		
		//test();
		for (int i = 0; i < 10; i++) {
			int n = (int)(Math.random()*100+1);
			int m = (int)(Math.random()*25+2);
			int arr11[] = {13,5,31,111,56,32};
			int arr12mul[] = {3};//{3,5,7,9};
			int arr12sum[] = {3};//{5,2,8,6};
			//int m=2;
			test11(arr11);
			submit(arr11,arr12mul,arr12sum,m,2);
		}
		
	
		
	}
	
	public static void submit(int arr11[],int arr12mul[],int arr12sum[],int m,int s){
		
		ArrayList<RawTaskData> follow = new ArrayList<>();
		ArrayList<TaskPackage<Double>> packages1 = new ArrayList<>();
		ArrayList<TaskPackage<Double>> packages2 = new ArrayList<>();
		long pId = 0;
		long mtId = TaskPackage.MULTIPLICATION_TASK;
		long stId = TaskPackage.SUMMATION_TASK;
		//Preparing 1.1
		for (int i = 0; i < arr11.length; i++) {
			int n = arr11[i];
			int amount = n/m;
			if(n%m!=0){
				amount++;
			}
			for (int j = 1; j < arr11[i]+1; j+=m) {
				if(j+m < n+1){
					//System.out.println("["+j+","+(j+m)+"]");
					packages1.add(new TaskPackage<>(pId, mtId, amount, new BasicTask11(j, j+m)));
				}else{
					packages1.add(new TaskPackage<>(pId, mtId, amount, new BasicTask11(j, n+1)));
					//System.out.println("["+j+","+(n)+"]");
				}
			}
			follow.add(new RawTaskData(pId, mtId, amount));
			//System.out.println(new RawTaskData(pId, mtId, amount));
			pId++;
		}
		//1.2
		for (int i = 0; i < arr12mul.length + arr12sum.length; i++) {
			int n=0,amount=0;
			if(i<arr12mul.length){
				n = arr12mul[i];
				amount = n/m;
				if(n%m!=0){
					amount++;
				}
				for (int j = 1; j < arr12mul[i]+1; j+=m) {
					if(j+m < n+1){
						//System.out.println("["+j+","+(j+m)+"]");
						packages2.add(new TaskPackage<>(pId, mtId, amount, new BasicTask12mul(j, j+m)));
					}else{
						packages2.add(new TaskPackage<>(pId, mtId, amount, new BasicTask12mul(j, n+1)));
						//System.out.println("["+j+","+(n)+"]");
					}
				}
			}
			
			if(i<arr12sum.length){
				n = arr12sum[i];
				amount = n/s;
				if(n%m!=0){
					amount++;
				}
				for (int j = 1; j < arr12sum[i]+1; j+=m) {
					if(j+m < n+1){
						//System.out.println("["+j+","+(j+m)+"]");
						packages2.add(new TaskPackage<>(pId, mtId, amount, new BasicTask12mul(j, j+m)));
					}else{
						packages2.add(new TaskPackage<>(pId, mtId, amount, new BasicTask12mul(j, n+1)));
						//System.out.println("["+j+","+(n)+"]");
					}
				}
			}
			//System.out.println(new RawTaskData(pId, mtId, amount));
			if(i >= arr12sum.length && i >= arr12mul.length)break;
			follow.add(new RawTaskData(pId, mtId, amount));
			pId++;
		}
		
		
		
		Result<Double> result = new Result<>();
		PoolMagener<Double> pm = new PoolMagener<>(result, 10, 10);
		Feeder<Double> f1 =new Feeder<>(packages2, pm);
		pm.start();
		f1.start();
		
		while(true){
			int i=-1;
			while(!follow.isEmpty()){
				i=(i+1)%follow.size();
				RawTaskData data = follow.get(i);
				pId = data.getPackageID();
				ArrayList<Report<Double>> ans = result.getResultSet(pId, data.getTaskId(), data.getAmount());
				if(ans!=null){
					follow.remove(i);
					if(data.getPackageID()<arr11.length){
						cheack11(data, ans, arr11, m, follow, f1);
					}else{
						ArrayList<TaskPackage<Double>> mainTasks = new ArrayList<>();
						ArrayList<Report<Double>> mul = new ArrayList<>();
						ArrayList<Report<Double>> sum = new ArrayList<>();
						for (int j = 0; j < ans.size(); j++) {
							Report<Double> r = ans.get(i);
							if(r.getTaskId()==TaskPackage.MULTIPLICATION_TASK){
								mul.add(r);
							}else{
								sum.add(r);
							}
						}
						//Multiplication 1.2
						int n = mul.size();
						int amount = mul.size()/m;
						if(ans.size()%m!=0){
							amount++;
						}
						pId = data.getPackageID();
						List<Report<Double>> subList;
						for (int j = 0; j < mul.size(); j+=m) {
							if(j+m < n){
								subList = mul.subList(j, j+m);
							}else{
								subList = mul.subList(j, n);
							}
							mainTasks.add(new TaskPackage<>(pId, TaskPackage.MULTIPLICATION_TASK,amount, new MainTaskMul(subList)));
						}
						follow.add(new RawTaskData(pId, TaskPackage.MULTIPLICATION_TASK, amount));
						//Summation 1.2
						n = sum.size();
						amount = n/s;
						if(n%s!=0){
							amount++;
						}
						for (int j = 0; j < sum.size(); j+=s) {
							if(j+m < n){
								subList = mul.subList(j, j+s);
							}else{
								subList = mul.subList(j, n);
							}
							mainTasks.add(new TaskPackage<>(pId, TaskPackage.SUMMATION_TASK,amount, new MainTaskSum(subList)));
						}
						f1.addSet(mainTasks);
					}
				}
				
			}
		}
	}
	
	public static void cheack11(RawTaskData data,ArrayList<Report<Double>> ans,int[] arr11,int m,ArrayList<RawTaskData> follow,Feeder<Double> f1){
		
		if(data.getAmount()==1){
			Report<Double> r = ans.get(0);
			System.out.println("Expr. type (1.1), n = "+arr11[(int)r.getPackageId()]+" : "+r.getResult());
		}else{
			ArrayList<TaskPackage<Double>> mainTasks = new ArrayList<>();
			int n = ans.size();
			int amount = ans.size()/m;
			if(ans.size()%m!=0){
				amount++;
			}
			long pId = data.getPackageID();
			List<Report<Double>> subList;
			for (int j = 0; j < n; j+=m) {
				if(j+m < n){
					subList = ans.subList(j, j+m);
				}else{
					subList = ans.subList(j, n);
				}
				mainTasks.add(new TaskPackage<>(pId, TaskPackage.MULTIPLICATION_TASK, amount, new MainTaskMul(subList)));
			}
			follow.add(new RawTaskData(pId, TaskPackage.MULTIPLICATION_TASK, amount));
			f1.addSet(mainTasks);
		}
	}
	
	
	
	public static void test11(int arr11[]){
		for (int i = 0; i < arr11.length; i++) {
			BasicTask11 t = new BasicTask11(1, arr11[i]+1);
			try {
				System.out.println("         TEST   , n = "+arr11[i]+" : "+t.call());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
