package larsworks.datetool.ui.dnd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.dnd.TransferData;

public class StringArrayType extends TransferData {
	private final List<String> strings = new ArrayList<String>();
	
	public void add(String str) {
		strings.add(str);
	}
	
	public List<String> getList() {
		return new ArrayList<String>(strings);
	}
	
	public int size() {
		return strings.size();
	}
	
}
