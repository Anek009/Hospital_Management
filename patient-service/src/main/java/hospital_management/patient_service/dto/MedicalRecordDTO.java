
    package hospital_management.patient_service.dto;

import java.time.LocalDate;

    public class MedicalRecordDTO {
        private Long id;
        private String diagnosis;
        private String treatment;
        private LocalDate recordDate;
        private String filePath;
        private Long patientId;

        public MedicalRecordDTO() {}

        public MedicalRecordDTO(Long id, String diagnosis, String treatment, LocalDate recordDate, String filePath, Long patientId) {
            this.id = id;
            this.diagnosis = diagnosis;
            this.treatment = treatment;
            this.recordDate = recordDate;
            this.filePath = filePath;
            this.patientId = patientId;
        }

        // Getters and Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public Long getPatientId() {
            return patientId;
        }

        public void setPatientId(Long patientId) {
            this.patientId = patientId;
        }
    }


