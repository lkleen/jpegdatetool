package larsworks.datetool.configuration.xml;

import javax.xml.bind.annotation.XmlElement;

import larsworks.datetool.configuration.ThumbConfiguration;
import larsworks.datetool.configuration.ThumbSize;

public class XMLThumbConfiguration implements ThumbConfiguration {

	@XmlElement(name = "IconSize", required = true)
	private XMLThumbSize iconSize = new XMLThumbSize(30, 30);

	@XmlElement(name = "PreviewSize", required = true)
	private XMLThumbSize previewSize = new XMLThumbSize(1024, 1024);
	
	@XmlElement(name = "SmallPreviewSize", required = true)
	private XMLThumbSize smallPreviewSize = new XMLThumbSize(80, 80);

	public ThumbSize getIconSize() {
		return iconSize;
	}

	public void setIconSize(XMLThumbSize iconSize) {
		this.iconSize = iconSize;
	}

	public ThumbSize getPreviewSize() {
		return previewSize;
	}

	public void setPreviewSize(XMLThumbSize previewSize) {
		this.previewSize = previewSize;
	}

	public ThumbSize getSmallPreviewSize() {
		return smallPreviewSize;
	}

	public void setSmallPreviewSize(XMLThumbSize smallPreviewSize) {
		this.smallPreviewSize = smallPreviewSize;
	}

}
