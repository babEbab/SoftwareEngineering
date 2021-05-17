package minipetdoorsystem.accessrecord;

public interface AccessRecordRepository {
    void save(AccessRecord accessRecord);

    AccessRecord findAccessRecord(int accessRecordId);
}
