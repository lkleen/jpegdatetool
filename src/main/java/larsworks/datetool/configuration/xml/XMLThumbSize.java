package larsworks.datetool.configuration.xml;

import javax.xml.bind.annotation.XmlAttribute;

import larsworks.datetool.configuration.ThumbSize;

public class XMLThumbSize implements ThumbSize {

	@XmlAttribute(name = "height", required = true)
	private int height;

	@XmlAttribute(name = "width", required = true)
	private int width;

	public XMLThumbSize() {}
	
	public XMLThumbSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int hashCode() {
		return width*31 + height*17;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof ThumbSize) {
			ThumbSize ts = (ThumbSize) other;
			return (ts.getWidth() == this.getWidth()) && (ts.getHeight() == this.getHeight());
		}
		return false;
	}

}
