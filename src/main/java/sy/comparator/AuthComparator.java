package sy.comparator;

import java.util.Comparator;
/**
 * Auth排序
 * 
 * @author 孙宇
 * 
 */
public class AuthComparator implements Comparator<sy.model.Tauth> {

	public int compare(sy.model.Tauth o1, sy.model.Tauth o2) {
		int i1 = o1.getSeq() != null ? o1.getSeq().intValue() : -1;
		int i2 = o2.getSeq() != null ? o2.getSeq().intValue() : -1;
		return i1 - i2;
	}

}
