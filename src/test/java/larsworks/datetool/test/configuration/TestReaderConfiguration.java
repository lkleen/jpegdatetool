package larsworks.datetool.test.configuration;

import larsworks.datetool.configuration.ReaderConfiguration;

public class TestReaderConfiguration implements ReaderConfiguration {

	boolean recursive = true;
	
	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}
	
}
