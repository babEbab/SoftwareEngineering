package minipetdoorsystem;

public class Door {

    Boolean state = false;

    public void openDoor() {
        state = true;
        System.out.println("문이 열린다");
    }

    public void closeDoor() {
        state = false;
        System.out.println("문이 닫힌다");
    }
}