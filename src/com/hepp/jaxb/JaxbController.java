package com.hepp.jaxb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 * A demo class to show how to us JAXB to create an XML schema and to write and
 * read XML files
 * 
 * @author elmar.hepp@gmail.com
 *
 */
public class JaxbController {

	public static void main(String[] args) {
		JaxbController controller = new JaxbController();
		String xmlFilename = "cdstore.xml";

		controller.createSchema();
		controller.writeXML(xmlFilename);
		controller.readXML(xmlFilename);
	}

	/**
	 * Create a schema
	 */
	private void createSchema() {
		System.out.println(">>Create Schema");

		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(CDStore.class);
			jc.generateSchema(new SchemaOutputResolver() {

				@Override
				public Result createOutput(String namespaceURI, String suggestedFileName)
						throws IOException {
					StreamResult result = new StreamResult(new File("xxx"));
					result.setSystemId(suggestedFileName);
					return result;
				}
			});
		} catch (JAXBException e) {
			System.err.println("JAXBException: " + e);
		} catch (IOException e) {
			System.err.println("IOException: " + e);
		}
	}

	/**
	 * Create an XML file
	 * 
	 * @param xmlFilename
	 */
	private void writeXML(String xmlFilename) {
		System.out.println(">>writeXML");

		ArrayList<CD> cdList = new ArrayList<CD>();

		// create CDs
		CD cd1 = new CD();
		cd1.setArtist("Karajan");
		cd1.setTitle("Don Carlos");
		cd1.setYear("1975");

		CD cd2 = new CD();
		cd2.setArtist("Abbado");
		cd2.setTitle("Don Giovanni");
		cd2.setYear("1991");

		// create CDStore
		CDStore cdStore = new CDStore();
		cdStore.setCdName("CD Butterfly Store");
		cdStore.getCdList().add(cd1);
		cdStore.getCdList().add(cd2);

		// create JAXB context and Marshaller
		try {
			JAXBContext context = JAXBContext.newInstance(CDStore.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// write in file
			marshaller.marshal(cdStore, new File(xmlFilename));
		} catch (JAXBException e) {
			System.err.println("JAXBException: " + e);
		}
	}

	/**
	 * Read an XML file
	 * 
	 * @param xmlFilename
	 */
	private void readXML(String xmlFilename) {
		System.out.println(">>readXML");

		// create JAXB context and Unmarshaller
		try {
			JAXBContext context = JAXBContext.newInstance(CDStore.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// read in file
			CDStore cdStore = (CDStore) unmarshaller.unmarshal(new FileReader(xmlFilename));
			List<CD> cdList = cdStore.getCdList();
			for (CD cd : cdList) {
				System.out.println("Title: " + cd.getTitle() + ", Artist: " + cd.getArtist());
			}

		} catch (JAXBException e) {
			System.err.println("JAXBException: " + e);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e);
		}

	}

}
