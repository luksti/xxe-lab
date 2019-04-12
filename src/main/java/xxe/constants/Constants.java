package xxe.constants;

public class Constants {
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

    public static final String XXE_FREE = "4248ca35ac8bacafd36a0205c890494a";
    public static final String XXE_EVIL = "82bf75b9d118715a9094064cefac3fd7";
    public static final String OBJ1 = "obj1";
    public static final String OBJ2 = "obj2";
    public static final String OBJ3 = "obj3";
    public static final String OBJ4 = "obj4";
    public static final String OBJ5 = "obj5";
}
