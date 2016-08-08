package logic;

import java.util.List;
import java.util.Set;

public interface DataUtil {
    List<String> readDates(String month);
    Set<Item> readDay(String month, String date);
    void createDay(String month, String date) throws Exception;
    //todo use item instead of colum etc..
    void updateDay(String month, String date, String column, String newValue, String item) throws Exception;
    void insertItem(String month, String date, String itemName) throws Exception;
    void deleteItem(String month, String date, String itemName) throws Exception;
    double getTotalValue(String month, String date);
}
