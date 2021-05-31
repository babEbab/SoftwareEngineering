package PetDoorReverseEngineering;

import java.util.Timer;
import java.util.TimerTask;

public class PetDoorSystemTest {

    public static void main(String[] args) throws InterruptedException {
        // 애완동물의 출입 시도가 10초 간격으로 이루어지도록 타이머 설정
        Timer timer = new Timer();

        // 펫도어에 필요한 객체 생성
        DogDoor door = new DogDoor();
        BarkRecognizer barkRecognizer = new BarkRecognizer(door);

        // dogDoor에 내 애완동물의 소리 정보 추가
        Bark bark1 = new Bark("abc");
        Bark bark2 = new Bark("123");
        door.addAllowedBark(bark1);
        door.addAllowedBark(bark2);

        // dogDoor가 닫혀있는지 확인 후 내 애완동물 출입 시도
        System.out.println("Is door opened? " + door.isOpen());
        barkRecognizer.recognize(bark1);

        // dogDoor가 닫혀있는지 확인 후 내 애완동물 출입 시도
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Is door opened? " + door.isOpen());
                barkRecognizer.recognize(bark2);
            }
        }, 10000);

        // dogDoor가 닫혀있는지 확인 후 다른 애완동물 출입 시도
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Is door opened? " + door.isOpen());
                Bark otherBark1 = new Bark("159");
                barkRecognizer.recognize(otherBark1);
            }
        }, 20000);
    }
}