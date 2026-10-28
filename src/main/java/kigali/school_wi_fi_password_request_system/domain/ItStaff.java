package kigali.school_wi_fi_password_request_system.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "it_staff")
public class ItStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "computer_lab_id")
    private ComputerLab computerLab;

    @ManyToMany
    @JoinTable(name = "it_staff_duty",
            joinColumns = @JoinColumn(name = "it_staff_id"),
            inverseJoinColumns = @JoinColumn(name = "duty_id"))
    private List<Duty> duties = new ArrayList<>();

    public UUID getId() { return id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public ComputerLab getComputerLab() { return computerLab; }
    public void setComputerLab(ComputerLab computerLab) { this.computerLab = computerLab; }

    public List<Duty> getDuties() { return duties; }
    public void setDuties(List<Duty> duties) { this.duties = duties; }
}