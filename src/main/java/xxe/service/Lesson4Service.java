package xxe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import xxe.domain.People;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Lesson4Service {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<Integer, People> peopleList2 = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;

    static {
        People people = new People();
        people.setId(currentId);
        people.setName("John Snow");
        people.setAddress("North Pole 14");
        people.setComment("Winter is coming");
        peopleList2.put(currentId++, people);
        people = new People();
        people.setId(currentId);
        people.setName("John Smith");
        people.setAddress("Cowan Way 9");
        people.setComment("divorced");
        peopleList2.put(currentId++, people);
    }

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

// based on https://www.codota.com/code/java/classes/org.xml.sax.helpers.XMLReaderFactory
    void processXml(String xmlString) {
        People people;
                try {
                    XMLReader reader = XMLReaderFactory.createXMLReader();
                    SAXSource source = new SAXSource(reader, new InputSource(new StringReader(xmlString)));

                    JAXBContext jc = JAXBContext.newInstance(People.class);
                    Unmarshaller um = jc.createUnmarshaller();

                    people = (People) um.unmarshal(source);

                    people.setId(currentId);
                    peopleList2.put(currentId++, people);
                } catch (SAXException | JAXBException e) {
                    e.printStackTrace();
                }
    }

    public List<People> getPeople() {
        List<People> result = new ArrayList<>();
        for (Map.Entry<Integer, People> entry : peopleList2.entrySet()) {
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
