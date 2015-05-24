package tasks;
public class BasicTask12mul extends BasicTask11{

	public BasicTask12mul(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double call() throws Exception {
		double ans = 1;
		int coefficient = 1;
		for (int i = getFromIndex(); i < getToIndex(); i++) {
			if(i%2!=0)coefficient = -1;
			ans *= coefficient/(2.0*i+3);
		}
		if(ans==1)return 0.0;
		return ans;
	}
	
	@Override
	public String toString() {
		return "BT12m[" + getFromIndex() + "," + getToIndex()
				+ ")";
	}

}
