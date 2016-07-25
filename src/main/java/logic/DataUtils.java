package logic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.*;

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

    public static Map<Long, String> readDates(String month) throws IOException{
        String path = pwd + "\\data\\" + month + ".csv";
        Map<Long, String> result = new TreeMap<Long, String>();

        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
        for (CSVRecord record : records) {
            result.put(record.getRecordNumber(), record.get("DATE"));
        }
        return result;
    }

}
