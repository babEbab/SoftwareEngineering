package minipetdoorsystem.pet;

import java.util.Optional;

public interface PetRepository {

    void save(Pet pet);

    Optional<Pet> findPet(int petId);
}
