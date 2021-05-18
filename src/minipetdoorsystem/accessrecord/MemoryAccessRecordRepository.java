package minipetdoorsystem.accessrecord;

import java.util.HashMap;
import java.util.Map;

public class MemoryAccessRecordRepository implements AccessRecordRepository {

    private static Map<Integer, AccessRecord> store = new HashMap<>();

    @Override
    public void save(AccessRecord accessRecord) {
        store.put(accessRecord.getId(), accessRecord);
    }

    @Override
    public AccessRecord findAccessRecord(int accessRecordId) {
        return store.get(accessRecordId);
    }
}