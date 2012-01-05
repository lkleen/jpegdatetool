package larsworks.datetool.util;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.*;

import org.apache.log4j.Logger;

public class XmlParser {

	private static Logger logger = Logger.getLogger(XmlParser.class);

	public static void marshal(Object jaxbObject, OutputStream os) {

		try {

			JAXBContext jc = JAXBContext.newInstance(jaxbObject.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty("jaxb.formatted.output", true);

			m.marshal(jaxbObject, os);

		} catch(JAXBException e) {
			logger.error(e);
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(InputStream is, Class<T> type) {

		try {

			JAXBContext jc = JAXBContext.newInstance(type);
			Unmarshaller u = jc.createUnmarshaller();

			T jaxbObject = (T) u.unmarshal(is);

			return jaxbObject;

		} catch(Exception e) {
			logger.error(e);
			return null;
		}
	}

}
