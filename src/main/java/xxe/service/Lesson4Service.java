package xxe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import xxe.constants.Constants;
import xxe.domain.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Lesson4Service {
    private static final Map<Integer, Person> peopleList2 = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;

    private final ZxcService zxcService;

    @Autowired
    public Lesson4Service(ZxcService zxcService) {
        this.zxcService = zxcService;
    }

    static {
        Person person = new Person();
        person.setId(currentId);
        person.setName("John Snow");
        person.setAddress("North Pole 14");
        person.setComment("Winter is coming");
        peopleList2.put(currentId++, person);
        person = new Person();
        person.setId(currentId);
        person.setName("John Smith");
        person.setAddress("Cowan Way 9");
        person.setComment("divorced");
        peopleList2.put(currentId++, person);
    }

// based on https://www.codota.com/code/java/classes/org.xml.sax.helpers.XMLReaderFactory
    void processXml(String xmlString) {
        Person person;
                try {
                    XMLReader reader = XMLReaderFactory.createXMLReader();
                    SAXSource source = new SAXSource(reader, new InputSource(new StringReader(xmlString)));

                    JAXBContext jc = JAXBContext.newInstance(Person.class);
                    Unmarshaller um = jc.createUnmarshaller();

                    person = (Person) um.unmarshal(source);

                    person.setId(currentId);
                    peopleList2.put(currentId++, person);
                } catch (SAXException | JAXBException e) {
                    e.printStackTrace();
                }
    }

    public List<Person> getPeople() {
        List<Person> result = new ArrayList<>();
        for (Map.Entry<Integer, Person> entry : peopleList2.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public boolean checkSolution() {
        processXml(Constants.XML);
        processXml(Constants.XXE_XML);

        return zxcService.checkSolution(getPeople());
    }

}
