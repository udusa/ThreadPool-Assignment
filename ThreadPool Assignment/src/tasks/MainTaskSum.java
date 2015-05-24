package tasks;

import java.util.List;

import pool.Report;

public class MainTaskSum extends MainTaskMul {

	public MainTaskSum(List<Report<Double>> ans) {
		super(ans);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Double call() throws Exception {
		double ans = 0;
		for (int i = 0; i < getAnswers().size(); i++) {
			ans += getAnswers().get(i).getResult();
		}
		
		return ans;
		
	}

}
