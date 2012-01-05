package larsworks.datetool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * JAXB based xml-reader and writer
 * 
 * @author lkleen
 * 
 */
public class XmlUtil {

	/**
	 * Writes from Object to xml file.
	 * 
	 * @param xml
	 *            path to xml file
	 * @param obj
	 *            JAXB object
	 * @throws FileNotFoundException
	 *             if file is not existing
	 */
	public static void write(String path, Object obj) throws FileNotFoundException {

		OutputStream os = new FileOutputStream(path);
		XmlParser.marshal(obj, os);

	}

	/**
	 * Reads from xml file and returns the accordingObject.
	 * 
	 * @param xml
	 *            path to xml file
	 * @param clazz
	 *            class declaration to map from (JAXB)
	 * @throws IOException
	 *             if file is not existing
	 */
	public static <T> T read(String path, Class<T> clazz) throws IOException {

		InputStream is = new FileInputStream(new File(path));
		return XmlParser.unmarshal(is, clazz);

	}

	/**
	 * Reads xml data from inputstream and returns the accordingObject.
	 * 
	 * @param <T>
	 *            class class declaration to map from (JAXB)
	 * @param is
	 *            inputstream to read from
	 * @param clazz
	 *            xml mapping class
	 * @return data class
	 * @throws IOException
	 */
	public static <T> T read(InputStream is, Class<T> clazz) throws IOException {

		return XmlParser.unmarshal(is, clazz);

	}

}
