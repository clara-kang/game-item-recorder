package logic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
        Map<Long, String> result = new TreeMap<Long, String>();
        List<CSVRecord> records = readTable(month);

        for (CSVRecord record : records) {
            result.put(record.getRecordNumber(), record.get("DATE"));
        }
        return result;
    }

    public static CSVRecord readItems(String month, Long rowNumber) throws IOException{
        List<CSVRecord> records = readTable(month);
        int row = rowNumber.intValue();
        return records.get(row - 1);
    }

    private static List<CSVRecord> readTable(String month) throws IOException{
        String path = pwd + "\\data\\" + month + ".csv";
        Reader in = new FileReader(path);
        CSVParser parser = CSVFormat.EXCEL.withHeader().parse(in);
        List<CSVRecord> list = parser.getRecords();
        parser.close();
        return list;
    }

}
