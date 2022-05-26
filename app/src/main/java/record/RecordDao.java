package record;

import java.util.List;

interface RecordDao {
    void addRecord(String name, int score, String time, String difficulty);
    void updateRecords(String name,int score);
    void deleteRecords(String time);
    List<PlayerRecord> getAllRecords();
}
