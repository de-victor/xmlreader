package br.com.greenmile.xmlreader.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.greenmile.xmlreader.enity.Coordenate;

@Service
public class ProcessXmlService {

	private final String xmlTag = "trkpt";
	
	public List<Coordenate> processXml(String filePath) {
		List<Coordenate> route = new ArrayList<>();
		try {
			
			File xml = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(xmlTag);

			for (int i = 0; i < nList.getLength(); i++) {
				Node item = nList.item(i);

				if (item.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) item;
					
					route.add(new Coordenate(element.getAttribute("lat"), element.getAttribute("lon"), element.getTextContent()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return route;
	}
}
