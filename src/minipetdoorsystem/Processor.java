package minipetdoorsystem;

import minipetdoorsystem.accessrecord.AccessRecord;
import minipetdoorsystem.accessrecord.AccessRecordRepository;
import minipetdoorsystem.accessrecord.MemoryAccessRecordRepository;
import minipetdoorsystem.pet.MemoryPetRepository;
import minipetdoorsystem.pet.Pet;
import minipetdoorsystem.pet.PetRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class Processor {

    Boolean isProcessorTurnedOn = false;
    int AccessRecordCount = 0;
    private AccessRecordRepository accessRecordRepository = new MemoryAccessRecordRepository();
    private PetRepository petRepository = new MemoryPetRepository();
    private Door door = new Door();
    LocalTime time = LocalTime.now();
    LocalDate date = LocalDate.now();

    // processor에 싱글톤 패턴 적용
    private static final Processor instance = new Processor();

    public static Processor getInstance() {
        return instance;
    }

    private Processor() {
    }

    public void turnOnProcessor() { // 프로세서 켜기
        isProcessorTurnedOn = true;
    }

    public void turnOffProcessor() { // 프로세서 끄기
        isProcessorTurnedOn = false;
    }

    public void addPet(int petId, String petName) { // 동물 추가하기
        if (!isProcessorTurnedOn) {
            System.out.println("작업 실패. 원인: 프로세서가 꺼져있다");
            return;
        }
        Pet pet = new Pet(petId, petName);
        petRepository.save(pet);
        System.out.println("동물이름: " + petName + " 추가 성공");
    }

    void recognizePet(int petId) { // 마이크로칩 읽어서 동물 인식하여 문 열어주기
        if (!isProcessorTurnedOn) {
            System.out.println("작업 실패. 원인: 프로세서가 꺼져있다");
            return;
        }
        Optional pet = petRepository.findPet(petId);
        if (pet != Optional.empty()) {
            putAccessRecord(petId);
            door.openDoor();
            LocalTime startTime = time;
            Duration du;
            while (true) {
                du = Duration.between(startTime, time);
                if (du.getSeconds() >= 10) { // 문을 연 시각과 현재 시각이 10초 이상 차이가 나면 문을 닫는다.
                    door.closeDoor();
                    break;
                }
                if (Math.random() >= 0.7) { // 동물이 인식 범위를 벗어나면 문을 닫는다.
                    door.closeDoor();
                    break;
                }
            }
        }
        else {
            putAccessRecord();
            System.out.println("작업 실패. 원인: 동물 인식 실패로 문이 열리지 않는다");
        }
    }

    void putAccessRecord() { // 출입 실패 정보 기록
        AccessRecord accessRecord = new AccessRecord(++AccessRecordCount, "Unknown", date, time);
        accessRecordRepository.save(accessRecord);
    }

    void putAccessRecord(int petId) { // 출입 성공 정보 기록
        String petName = petRepository.findPet(petId).get().getName();
        AccessRecord accessRecord = new AccessRecord(++AccessRecordCount, petName,date, time);
        accessRecordRepository.save(accessRecord);
    }

    public AccessRecord[] makeReport() { // 출입 기록 보고서 만들기
        if (!isProcessorTurnedOn) {
            return null;
        }
        AccessRecord[] report = new AccessRecord[AccessRecordCount];
        for (int i = 1; i <= AccessRecordCount; i++) {
            report[i - 1] = accessRecordRepository.findAccessRecord(i);
        }
        return report;
    }
}