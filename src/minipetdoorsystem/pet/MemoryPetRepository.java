package minipetdoorsystem.pet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryPetRepository implements PetRepository {

    private static Map<Integer, Pet> store = new HashMap<>();

    @Override
    public void save(Pet pet) {
        store.put(pet.getId(), pet);
    }

    @Override
    public Optional<Pet> findPet(int petId) {
        return Optional.ofNullable(store.get(petId));
    }

}