package kigali.school_wi_fi_password_request_system.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "computer_lab")
public class ComputerLab {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "lab_number")
    private int labNumber;

    @Column(name = "name")
    private String name;

    public UUID getId() { return id; }

    public int getLabNumber() { return labNumber; }
    public void setLabNumber(int labNumber) { this.labNumber = labNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}