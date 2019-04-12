package xxe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import xxe.domain.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Lesson1Service {

    private static final Map<Integer, Person> peopleList = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;

    static {
        Person person = new Person();
        person.setId(currentId);
        person.setName("John Snow");
        person.setAddress("Castle Black");
        person.setComment("Winter is coming");
        peopleList.put(currentId++, person);
        person = new Person();
        person.setId(currentId);
        person.setName("John Smith");
        person.setAddress("Cowan Way 9");
        person.setComment("Divorced");
        peopleList.put(currentId++, person);
    }

// based on https://howtodoinjava.com/jaxb/read-xml-to-java-object/
    public void processXml(MultipartFile file) {
        Person person;
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            convFile.createNewFile();
            fos.write(file.getBytes());

            SAXParserFactory spf = SAXParserFactory.newInstance();
            Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(),
                    new InputSource(convFile.toString()));
            JAXBContext jc = JAXBContext.newInstance(Person.class);
            Unmarshaller um = jc.createUnmarshaller();

            person = (Person) um.unmarshal(xmlSource);

            person.setId(currentId);
            peopleList.put(currentId++, person);
        } catch (JAXBException | ParserConfigurationException | SAXException | IOException e) {
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

}
