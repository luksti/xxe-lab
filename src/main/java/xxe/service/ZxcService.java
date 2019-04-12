package xxe.service;

import org.springframework.stereotype.Service;
import xxe.constants.Constants;
import xxe.domain.Person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ZxcService {
    private void zxc(String obj) {
        byte[] data = "1".getBytes();
        try (FileOutputStream fos = new FileOutputStream(new File("obj/" + obj))) {
            fos.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zxcF(String obj) {
        byte[] data = "0".getBytes();
        try (FileOutputStream fos = new FileOutputStream(new File("obj/" + obj))) {
            fos.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSolution(String obj, List<Person> people) {
        boolean xxeResult = false;
        boolean xxeFreeResult = false;

        for (Person person : people) {
            if (person.getComment().equals(Constants.XXE_FREE)) {
                xxeFreeResult = true;
            }
            if (person.getAddress().equals(Constants.XXE_EVIL)) {
                xxeResult = true;
            }
        }

        if (!xxeResult && xxeFreeResult) {
            zxc(obj);
        } else {
            zxcF(obj);
        }
        return !xxeResult && xxeFreeResult;
    }
}
