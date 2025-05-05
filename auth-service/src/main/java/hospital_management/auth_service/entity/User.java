package hospital_management.auth_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    private String contactNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String status = "PENDING"; // WAITING_FOR_APPROVAL, APPROVED

    private boolean enabled = true;

    public User(){}

    public User( String fullName, String email, String password, String contactNumber, Role role, String status, boolean enabled) {

        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.role = role;
        this.status = status;
        this.enabled = enabled;
    }





    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
