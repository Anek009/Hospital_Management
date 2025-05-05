package hospital_management.hospital_service.repository;


import hospital_management.hospital_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT max(roomNumber) FROM Room")
    public Integer getLastRoomNo();

    public Optional<Room> findByRoomNumber(Integer roomNumber);
}