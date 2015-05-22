package tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import pool.Report;

public class MainTask11 implements Callable<Double>{
	
	ArrayList<Report<Double>> answers;
	
	public MainTask11() {
		answers = new ArrayList<>();
	}
	
	public void addToCalc(Report<Double> r){
		answers.add(r);
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
