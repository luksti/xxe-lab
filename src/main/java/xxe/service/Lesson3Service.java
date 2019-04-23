package xxe.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xxe.constants.Constants;
import xxe.domain.Person;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Lesson3Service {

    private static final Map<Integer, Person> peopleList2 = new ConcurrentHashMap<>();
    private static volatile int currentId = 0;

    private final ZxcService zxcService;

    @Autowired
    public Lesson3Service(ZxcService zxcService) {
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


// based on http://www.javased.com/?api=org.dom4j.Document
    void processXml(String xmlString) {
                try {
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(new StringReader(xmlString));
                    Element rootElement = document.getRootElement();

                    List<Element> elements = rootElement.elements();
                    Person newPerson = new Person();
                    for (Element element : elements) {
                        if ("address".equals(element.getName())) {
                            newPerson.setAddress(element.getTextTrim());
                        }
                        if ("name".equals(element.getName())) {
                            newPerson.setName(element.getTextTrim());
                        }
                        if ("comment".equals(element.getName())) {
                            newPerson.setComment(element.getTextTrim());
                        }
                    }
                    newPerson.setId(currentId);
                    peopleList2.put(currentId++, newPerson);
                } catch (DocumentException e) {
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
