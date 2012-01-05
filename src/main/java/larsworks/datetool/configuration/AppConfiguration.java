package larsworks.datetool.configuration;

public interface AppConfiguration {
	/**
	 * @return base dir for the file chooser
	 */
	String getBaseDir();
	
	/**
	 * @return output dir for images
	 */
	String getOutputDir();
	
	/**
	 * @return suffix to add to every output file name
	 */
	String getOutputSuffix();
	
	void setBaseDir(String baseDir);
	void setOutputDir(String outputDir);
	void setOutputSuffix(String outputSuffix);
	
}
