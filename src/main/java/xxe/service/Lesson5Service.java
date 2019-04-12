package xxe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xxe.constants.Constants;
import xxe.domain.Person;

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

    private static final Map<Integer, Person> peopleList = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;
    private final ZxcService zxcService;

    @Autowired
    public Lesson5Service(ZxcService zxcService) {
        this.zxcService = zxcService;
    }

// based on https://www.programcreek.com/java-api-examples/?class=javax.xml.stream.XMLInputFactory&method=newFactory
    void processXml(String xmlString) {
        Person person;
                try {
                    XMLInputFactory xif = XMLInputFactory.newFactory();
                    XMLStreamReader xmlRead = xif.createXMLStreamReader(new StreamSource(new StringReader(xmlString)));
                    JAXBContext jc = JAXBContext.newInstance(Person.class);
                    Unmarshaller um = jc.createUnmarshaller();
                    person = (Person) um.unmarshal(xmlRead);
                    person.setId(currentId);
                    peopleList.put(currentId++, person);
                } catch (JAXBException | XMLStreamException e) {
                    e.printStackTrace();
                }
    }

    public List<Person> getPeople() {
        List<Person> result = new ArrayList<>();
        for (Map.Entry<Integer, Person> entry : peopleList.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public boolean checkSolution() {
        processXml(Constants.XML);
        processXml(Constants.XXE_XML);

        return zxcService.checkSolution(Constants.OBJ2, getPeople());
    }

}
