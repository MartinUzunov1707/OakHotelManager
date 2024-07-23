package bg.oakhotelmanager.init;

import bg.oakhotelmanager.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class RoomInitialiser implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public RoomInitialiser(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roomRepository.count()==0){

        }
    }
}
