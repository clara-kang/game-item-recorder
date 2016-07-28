package logic;

import java.util.List;
import java.util.Set;

public interface DataUtil {
    List<String> readDates(String month);
    Set<Item> readItems(String month, String date);
}
