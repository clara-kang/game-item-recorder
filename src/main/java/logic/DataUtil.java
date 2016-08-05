package logic;

import java.util.List;
import java.util.Set;

public interface DataUtil {
    List<String> readDates(String month);
    Set<Item> readDay(String month, String date);
    void createDay(String month, String date) throws Exception;
    //todo use item instead of colum etc..
    void updateDay(String month, String date, String column, int newValue, String item);
    void insertItem(String month, String date, String itemName);
    void deleteItem(String month, String date, String itemName);
}
