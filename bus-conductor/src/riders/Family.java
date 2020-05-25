package riders;

import java.util.ArrayList;

public class Family {
	protected Parent p;
	protected ArrayList<Children> c;
	
	public Family(Parent p, Children[] c) {
		this.p = p;
		this.c = new ArrayList<Children>();
	}
}
