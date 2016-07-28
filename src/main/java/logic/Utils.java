package logic;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
                result.add(listOfFiles[i].getName().replace(".mv.db", ""));
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
}
