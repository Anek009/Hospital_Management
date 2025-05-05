package hospital_management.patient_service.entity;

import hospital_management.patient_service.entity.Patient;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String diagnosis;
    private String treatment;
    private LocalDate recordDate;
    private String filePath;

    public MedicalRecord(Long id, Patient patient, String diagnosis, String treatment, LocalDate recordDate, String filePath) {
        this.id = id;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.recordDate = recordDate;
        this.filePath = filePath;
    }

    public MedicalRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
