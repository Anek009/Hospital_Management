package hospital_management.hospital_service.repository.admin;

import hospital_management.hospital_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT max(r.roomNumber) FROM Room r")
    Integer getLastRoomNo();

    Optional<Room> findByRoomNumber(Integer roomNumber);

    // Find first available room in department
    Optional<Room> findFirstByDepartment_DeptNameAndAvailableTrue(String departmentName);

    // Optional: if needed in service
    List<Room> findByDepartment_Id(int departmentId);
}
