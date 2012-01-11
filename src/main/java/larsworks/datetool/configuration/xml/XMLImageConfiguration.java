package larsworks.datetool.configuration.xml;

import javax.xml.bind.annotation.XmlElement;

import larsworks.datetool.configuration.ImageConfiguration;
import larsworks.datetool.configuration.ImageSize;

public class XMLImageConfiguration implements ImageConfiguration {

	@XmlElement(name = "IconSize", required = true)
	private XMLImageSize iconSize = new XMLImageSize(30, 30);

	@XmlElement(name = "PreviewSize", required = true)
	private XMLImageSize previewSize = new XMLImageSize(1024, 1024);
	
	@XmlElement(name = "SmallPreviewSize", required = true)
	private XMLImageSize smallPreviewSize = new XMLImageSize(80, 80);

	public ImageSize getIconSize() {
		return iconSize;
	}

	public void setIconSize(XMLImageSize iconSize) {
		this.iconSize = iconSize;
	}

	public ImageSize getPreviewSize() {
		return previewSize;
	}

	public void setPreviewSize(XMLImageSize previewSize) {
		this.previewSize = previewSize;
	}

	public ImageSize getSmallPreviewSize() {
		return smallPreviewSize;
	}

	public void setSmallPreviewSize(XMLImageSize smallPreviewSize) {
		this.smallPreviewSize = smallPreviewSize;
	}

}
