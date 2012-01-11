package larsworks.datetool.configuration;

public interface Configuration {
	ReaderConfiguration getReaderConfiguration();
	AppConfiguration getAppConfiguration();
	ImageConfiguration getThumbnailConfiguration();

	/**
	 * writes current configuration
	 */
	void store();
}
