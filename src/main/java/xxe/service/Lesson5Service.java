package xxe.service;

import org.springframework.stereotype.Service;
import xxe.domain.People;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Lesson5Service {

    private static final Map<Integer, People> peopleList = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;

    private static final String XXE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<!DOCTYPE people [\n" +
            "        <!ENTITY xxe SYSTEM \"http://enos.itcollege.ee/~luksti/XXE/xxe.txt\">\n" +
            "        ]>\n" +
            "<people>\n" +
            "    <address>&xxe;</address>\n" +
            "    <comment>11112222333</comment>\n" +
            "    <name>Kuri Fail</name>\n" +
            "</people>";

    private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<people>\n" +
            "    <name>Test</name>\n" +
            "    <comment>4248ca35ac8bacafd36a0205c890494a</comment>\n" +
            "    <address>Lauri Bentley</address>\n" +
            "</people>";

// based on https://www.programcreek.com/java-api-examples/?class=javax.xml.stream.XMLInputFactory&method=newFactory
    void processXml(String xmlString) {
        People people;
                try {
                    XMLInputFactory xif = XMLInputFactory.newFactory();
                    XMLStreamReader xmlRead = xif.createXMLStreamReader(new StreamSource(new StringReader(xmlString)));
                    JAXBContext jc = JAXBContext.newInstance(People.class);
                    Unmarshaller um = jc.createUnmarshaller();
                    people = (People) um.unmarshal(xmlRead);
                    people.setId(currentId);
                    peopleList.put(currentId++, people);
                } catch (JAXBException | XMLStreamException e) {
                    e.printStackTrace();
                }
    }

    public List<People> getPeople() {
        List<People> result = new ArrayList<>();
        for (Map.Entry<Integer, People> entry : peopleList.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public boolean checkSolution() {
        boolean xxeResult = false;
        boolean xxeFreeResult = false;
        processXml(XML);
        processXml(XXE_XML);

        for (People person : getPeople()) {
            if (person.getComment().equals("4248ca35ac8bacafd36a0205c890494a")) {
                xxeFreeResult = true;
            }
            if (person.getAddress().equals("82bf75b9d118715a9094064cefac3fd7")) {
                xxeResult = true;
            }
        }

        return !xxeResult && xxeFreeResult;
    }

}
