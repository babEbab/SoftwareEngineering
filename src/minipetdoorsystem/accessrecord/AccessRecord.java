package minipetdoorsystem.accessrecord;

import java.time.LocalDate;
import java.time.LocalTime;

public class AccessRecord {
    int id;
    String petName;
    LocalDate date;
    LocalTime time;

    public AccessRecord(int id, String petName, LocalDate date, LocalTime time) {
        this.id = id;
        this.petName = petName;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}