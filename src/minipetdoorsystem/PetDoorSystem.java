package minipetdoorsystem;

import minipetdoorsystem.app.AddPetApp;
import minipetdoorsystem.app.ControlApp;
import minipetdoorsystem.app.ReportApp;

import java.util.Scanner;

public class PetDoorSystem {
    Scanner sc = new Scanner(System.in);

    void processCommand() {
        while (true) {
            System.out.println("\n1. (애완동물) 문 열기");
            System.out.println("2. 프로세서 켜기");
            System.out.println("3. 프로세서 끄기");
            System.out.println("4. 애완동물 추가");
            System.out.println("5. 출입 기록 보고서 보기");
            System.out.println("0. 프로그램 종료");
            System.out.print("원하는 기능을 선택하시오: ");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    handleOpenDoor();
                    break;
                case 2:
                    handleTurnOnProcessor();
                    break;
                case 3:
                    handleTurnOffProcessor();
                    break;
                case 4:
                    handleAddPet();
                    break;
                case 5:
                    handleShowReport();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void handleOpenDoor() {
        Processor processor = Processor.getInstance();
        System.out.println("동물의 마이크로칩 입력");
        processor.recognizePet(sc.nextInt());
    }

    private void handleTurnOnProcessor() {
        ControlApp controlApp = ControlApp.getInstance();
        controlApp.turnOnProcessor();
    }

    private void handleTurnOffProcessor() {
        ControlApp controlApp = ControlApp.getInstance();
        controlApp.turnOffProcessor();    }

    private void handleAddPet() {
        AddPetApp addPetApp = AddPetApp.getInstance();
        System.out.println("동물의 마이크로칩을 인식시키고, 동물의 이름을 입력하세요.");
        addPetApp.addPet(sc.nextInt(), sc.next());
    }

    private void handleShowReport() {
        ReportApp reportApp = ReportApp.getInstance();
        reportApp.showReport();
    }

    public static void main(String[] args) {
        PetDoorSystem app = new PetDoorSystem();
        app.processCommand();
    }
}
