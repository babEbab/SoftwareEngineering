package minipetdoorsystem.app;

import minipetdoorsystem.Processor;

public class AddPetApp implements App {

    private static final AddPetApp instance = new AddPetApp();

    public static AddPetApp getInstance() {
        return instance;
    }

    private AddPetApp() {
    }

    public void addPet(int petId, String petName) {
        processor.addPet(petId, petName);
    }
}