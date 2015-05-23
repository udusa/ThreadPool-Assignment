package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import pool.Report;

public class MainTask11 implements Callable<Double>{
	
	List<Report<Double>> answers;
	
	public MainTask11(List<Report<Double>> ans) {
		answers = ans;
	}
	
	public void addToCalc(ArrayList<Report<Double>> ans){
		answers=ans;
	}
	
	@Override
	public Double call() throws Exception {

		double ans = 1;
		for (int i = 0; i < answers.size(); i++) {
			ans*=answers.get(i).getResult();
		}
		return ans;
	}
	
	@Override
	public String toString() {
		return "MainTask11 : "+answers.toString();
	}

}
