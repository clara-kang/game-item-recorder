package logic;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static String readCurrentDirectory() {
        return System.getenv("PWD");
    }

    public static List<String> readMonths() throws FileNotFoundException {
        String pwd = readCurrentDirectory();
        File folder = new File(pwd + "\\data");
        List<String> result = new ArrayList<String>();

        if(!folder.exists()) {
            throw new FileNotFoundException("no data directory found");
        }

        File[] listOfFiles = folder.listFiles();

        if(ArrayUtils.isEmpty(listOfFiles)) {
            return null;
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".mv.db") ) {
                System.out.println("File " + listOfFiles[i].getName());
                String month = listOfFiles[i].getName().replace(".mv.db", "");
                //todo change this hard coded line
                if ( !month.equals("items")) {
                    result.add(month);
                }
            }
        }

        return result;
    }

    public static Object[][] itemSetToObjectArray(Set setOfObject) {
        Object[][] result = new Object[setOfObject.size()][3];
        if(setOfObject != null) {
            Iterator<Item> itemIterator = setOfObject.iterator();
            int counter = 0;
            while (itemIterator.hasNext()) {
                Item item = itemIterator.next();
                Object[] arr = {item.getName(), item.getPrice(), item.getQuantity()};
                result[counter] = arr;
                counter ++;
            }
        }
        return result;
    }

    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }

    public static String getTodayMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
}
