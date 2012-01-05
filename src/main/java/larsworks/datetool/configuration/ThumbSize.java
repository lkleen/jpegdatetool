package larsworks.datetool.configuration;

public interface ThumbSize {
	
	public static enum Size {
		ICON,
		SMALL,
		MEDIUM,
		LARGE
	}
	
	Size getSize();
	int getWidth();
	int getHeight();
}
