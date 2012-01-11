package larsworks.datetool.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import larsworks.datetool.configuration.xml.XMLConfiguration;
import larsworks.datetool.util.XmlUtil;

public class DTConfiguration implements Configuration {

	private static final String filename = "configuration.xml";
	private final XMLConfiguration conf;
	
	public DTConfiguration() {
		conf = getXMLConfiguration();
	}
	
	private static XMLConfiguration getXMLConfiguration() {
		File f = new File(filename);
		XMLConfiguration conf = null;
		if(f.exists()) {
			try {
				InputStream is = new FileInputStream(f);
				conf = XmlUtil.read(is, XMLConfiguration.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				conf = new XMLConfiguration();
				XmlUtil.write(filename, conf);
				// return written file to avoid null values
				return getXMLConfiguration();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conf;
	}
	
	@Override
	public AppConfiguration getAppConfiguration() {
		return conf.getAppConfiguration();
	}

	@Override
	public ReaderConfiguration getReaderConfiguration() {
		return conf.getReaderConfiguration();
	}

	@Override
	public ImageConfiguration getThumbnailConfiguration() {
		return conf.getThumbConfiguration();
	}

	@Override
	public void store() {
		try {
			XmlUtil.write(filename, conf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
