package larsworks.datetool.configuration;

public interface Configuration {
	ReaderConfiguration getReaderConfiguration();
	AppConfiguration getAppConfiguration();
	ThumbConfiguration getThumbnailConfiguration();

	/**
	 * writes current configuration
	 */
	void store();
}
