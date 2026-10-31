package kigali.school_wi_fi_password_request_system.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "access_grant")
public class AccessGrant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "network_name")
    private String networkName;

    @Column(name = "notes")
    private String notes;

    @Column(name = "valid_days")
    private int validDays;

    @Column(name = "granted_date")
    private LocalDate grantedDate;

    @ManyToOne
    @JoinColumn(name = "wifi_request_id")
    private WifiRequest wifiRequest;

    public UUID getId() { return id; }

    public String getNetworkName() { return networkName; }
    public void setNetworkName(String networkName) { this.networkName = networkName; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getValidDays() { return validDays; }
    public void setValidDays(int validDays) { this.validDays = validDays; }

    public LocalDate getGrantedDate() { return grantedDate; }
    public void setGrantedDate(LocalDate grantedDate) { this.grantedDate = grantedDate; }

    public WifiRequest getWifiRequest() { return wifiRequest; }
    public void setWifiRequest(WifiRequest wifiRequest) { this.wifiRequest = wifiRequest; }
}