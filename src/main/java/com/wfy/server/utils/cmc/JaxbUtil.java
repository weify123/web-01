package com.wfy.server.utils.cmc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxbUtil {
	public static Object xmlToObj(String xml, Class<?> cla)
			throws JAXBException {
		StringReader xmlStr = new StringReader(xml);
		JAXBContext context = JAXBContext.newInstance(cla);
		Unmarshaller um = context.createUnmarshaller();
		Object obj = um.unmarshal(xmlStr);
		return obj;
	}

	public static Object xmlToObj(InputStream in, Class<?> cla)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(cla);
		Unmarshaller um = context.createUnmarshaller();
		Object obj = um.unmarshal(in);
		return obj;
	}

	public static String objToXml(Object obj) throws JAXBException {
		StringWriter xmlStr = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(obj, xmlStr);
		return xmlStr.toString();
	}
}
