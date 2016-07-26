package logic;

import org.apache.commons.csv.CSVRecord;

import java.util.Iterator;
import java.util.Map;

public class Utils {

    public static String readCurrentDirectory() {
        return System.getenv("PWD");
    }

    public static String getItemHtmlPage(CSVRecord row) {
        Map<String, String> records = row.toMap();
        String result = "";
        result += "<table><tr><th>Item</th><th>Quantity</th></tr>";
        Iterator<String> itr1 = records.keySet().iterator();
        while(itr1.hasNext()){
            String key = itr1.next();
            if(key.equals("DATE")){
                continue;
            }
            result += "<tr><td>" + key + "</td><td>" + records.get(key) + "</td></tr>";
        }
        result += "</table>";

        return result;
    }
}
