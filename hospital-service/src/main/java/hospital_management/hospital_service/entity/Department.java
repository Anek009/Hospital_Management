package hospital_management.hospital_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String deptName;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Room> rooms = new ArrayList<>();
}