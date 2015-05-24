package tasks;

public class BasicTask12sum extends BasicTask11{

	public BasicTask12sum(int fromIndex, int toIndex) {
		super(fromIndex, toIndex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double call() throws Exception {
		
		double ans = 0;
		for (int i = getFromIndex(); i < getToIndex(); i++) {
			ans += i/(2.0*i*i+1);
		}
		return ans;
		
	}
	
	@Override
	public String toString() {
		return "BT12s[" + getFromIndex() + "," + getToIndex()
				+ ")";
	}
}
