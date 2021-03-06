package larsworks.datetool.configuration.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import larsworks.datetool.configuration.AppConfiguration;
import larsworks.datetool.configuration.ReaderConfiguration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class XMLConfiguration {

	@XmlElement(name = "AppConfiguration", required = true)
	private XMLAppConfiguration appConfiguration = new XMLAppConfiguration();

	@XmlElement(name = "ReaderConfiguration", required = true)
	private XMLReaderConfiguration readerConfiguration = new XMLReaderConfiguration();

	@XmlElement(name = "ThumbConfiguration", required = true)
	private XMLImageConfiguration thumbConfiguration = new XMLImageConfiguration();
	
	public static class XMLAppConfiguration implements AppConfiguration {

		@XmlElement(name = "BaseDir", required = true)
		private String base = "";

		@XmlElement(name = "OutputDir", required = true)
		private String output = "";

		@XmlElement(name = "OutputSuffix", required = true)
		private String suffix = "";

		@Override
		public String getBaseDir() {
			return base;
		}

		@Override
		public String getOutputDir() {
			return output;
		}

		@Override
		public void setBaseDir(String baseDir) {
			this.base = baseDir;
		}

		@Override
		public void setOutputDir(String outputDir) {
			this.output = outputDir;
		}

		@Override
		public String getOutputSuffix() {
			return suffix;
		}

		@Override
		public void setOutputSuffix(String outputSuffix) {
			this.suffix = outputSuffix;
		}

	}

	public static class XMLReaderConfiguration implements ReaderConfiguration {

		@XmlAttribute(name = "Recursive", required = true)
		private boolean recursive = false;

		@Override
		public boolean isRecursive() {
			return recursive;
		}

	}

	public XMLAppConfiguration getAppConfiguration() {
		return appConfiguration;
	}

	public XMLReaderConfiguration getReaderConfiguration() {
		return readerConfiguration;
	}

	public XMLImageConfiguration getThumbConfiguration() {
		return thumbConfiguration;
	}

	public void setThumbConfiguration(XMLImageConfiguration thumbConfiguration) {
		this.thumbConfiguration = thumbConfiguration;
	}

}
