package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import pool.Report;

public class MainTaskMul implements Callable<Double>{
	
	List<Report<Double>> answers;
	
	public MainTaskMul(List<Report<Double>> ans) {
		answers = ans;
	}
	
//	public void addToCalc(ArrayList<Report<Double>> ans){
//		answers=ans;
//	}
	
	@Override
	public Double call() throws Exception {
		double ans = 1;
		for (int i = 0; i < answers.size(); i++) {
			ans*=answers.get(i).getResult();
		}
		return ans;
	}
	
	
	
	protected List<Report<Double>> getAnswers() {
		return answers;
	}

	@Override
	public String toString() {
		return "MainTask11 : "+answers.toString();
	}

}
