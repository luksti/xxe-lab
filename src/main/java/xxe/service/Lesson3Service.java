package xxe.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import xxe.domain.People;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Lesson3Service {

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

    public static final String XXE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<!DOCTYPE people [\n" +
            "        <!ENTITY xxe SYSTEM \"http://enos.itcollege.ee/~luksti/XXE/xxe.txt\">\n" +
            "        ]>\n" +
            "<people>\n" +
            "    <address>&xxe;</address>\n" +
            "    <comment>11112222333</comment>\n" +
            "    <name>Kuri Fail</name>\n" +
            "</people>";

    public static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<people>\n" +
            "    <name>Test</name>\n" +
            "    <comment>4248ca35ac8bacafd36a0205c890494a</comment>\n" +
            "    <address>Lauri Bentley</address>\n" +
            "</people>";

// based on http://www.javased.com/?api=org.dom4j.Document
    public void processXml(String xmlString) {
                try {
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(new StringReader(xmlString));
                    Element rootElement = document.getRootElement();

                    List<Element> elements = rootElement.elements();
                    People newPeople = new People();
                    for (Element element : elements) {
                        if ("address".equals(element.getName())) {
                            newPeople.setAddress(element.getTextTrim());
                        }
                        if ("name".equals(element.getName())) {
                            newPeople.setName(element.getTextTrim());
                        }
                        if ("comment".equals(element.getName())) {
                            newPeople.setComment(element.getTextTrim());
                        }
                    }
                    newPeople.setId(currentId);
                    peopleList2.put(currentId++, newPeople);
                } catch (DocumentException  e) {
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
