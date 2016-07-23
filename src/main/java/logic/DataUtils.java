package logic;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    private static String pwd = Utils.readCurrentDirectory();
    private static File folder = new File(pwd + "\\data");

    public static List<String> readMonthTableNames() throws FileNotFoundException{
        List<String> result = new ArrayList<String>();

        if(!folder.exists()) {
            throw new FileNotFoundException("no data directory found");
        }

        File[] listOfFiles = folder.listFiles();

        if(ArrayUtils.isEmpty(listOfFiles)) {
            return null;
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && FilenameUtils.getExtension(listOfFiles[i].getAbsolutePath()).equals("csv") ) {
                System.out.println("File " + FilenameUtils.getBaseName(listOfFiles[i].getAbsolutePath()));
                result.add(FilenameUtils.getBaseName(listOfFiles[i].getAbsolutePath()));
            }
        }

        return result;
    }

}
