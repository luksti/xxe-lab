package xxe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import xxe.domain.People;

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

    private static final Map<Integer, People> peopleList = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;

    static {
        People people = new People();
        people.setId(currentId);
        people.setName("John Snow");
        people.setAddress("Castle Black");
        people.setComment("Winter is coming");
        peopleList.put(currentId++, people);
        people = new People();
        people.setId(currentId);
        people.setName("John Smith");
        people.setAddress("Cowan Way 9");
        people.setComment("Divorced");
        peopleList.put(currentId++, people);
    }

// based on https://howtodoinjava.com/jaxb/read-xml-to-java-object/
    public void processXml(MultipartFile file) {
        People people;
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            convFile.createNewFile();
            fos.write(file.getBytes());

            SAXParserFactory spf = SAXParserFactory.newInstance();
            Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(),
                    new InputSource(convFile.toString()));
            JAXBContext jc = JAXBContext.newInstance(People.class);
            Unmarshaller um = jc.createUnmarshaller();

            people = (People) um.unmarshal(xmlSource);

            people.setId(currentId);
            peopleList.put(currentId++, people);
        } catch (JAXBException | ParserConfigurationException | SAXException | IOException e) {
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

}
