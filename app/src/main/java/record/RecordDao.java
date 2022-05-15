package record;

import java.util.List;

interface RecordDao {
    void addRecord(int score, String time);
    void updateRecords();
    List<PlayerRecord> getAllRecords();
}
