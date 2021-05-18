package minipetdoorsystem.app;

public class ControlApp implements App {

    private static final ControlApp instance = new ControlApp();

    public static ControlApp getInstance() {
        return instance;
    }

    private ControlApp() {
    }

    public void turnOnProcessor() {
        processor.turnOnProcessor();
        System.out.println("프로세서 전원 켜기 성공");
    }

    public void turnOffProcessor() {
        processor.turnOffProcessor();
        System.out.println("프로세서 전원 끄기 성공");
    }
}
