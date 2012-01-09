package larsworks.datetool.configuration.xml;

import javax.xml.bind.annotation.XmlElement;

import larsworks.datetool.configuration.ThumbConfiguration;
import larsworks.datetool.configuration.ThumbSize;

public class XMLThumbConfiguration implements ThumbConfiguration {

	@XmlElement(name = "IconSize")
	private XMLThumbSize iconSize;

	@XmlElement(name = "PreviewSize")
	private XMLThumbSize previewSize;

	@XmlElement(name = "SmallPreviewSize")
	private XMLThumbSize smallPreviewSize;

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
