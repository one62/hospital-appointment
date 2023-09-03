package exercise.onemo.hospitalappointment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "treatments")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Treatment {
	@Id @GeneratedValue @Column(name = "treatment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@Enumerated(EnumType.STRING)
	private TreatmentStatus status;

	private LocalDateTime time;

	public void setPatient(Patient patient) {
		this.patient = patient;
		patient.getTreatments().add(this);
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
		doctor.getTreatments().add(this);
	}

	public static Treatment createTreatment(Patient patient, Doctor doctor, TreatmentStatus status, LocalDateTime time) {
		Treatment treatment = new Treatment();
		treatment.setPatient(patient);
		treatment.setDoctor(doctor);
		treatment.setStatus(status);
		treatment.setTime(time);
		return treatment;
	}

	public void cancel() {
		if (this.getStatus() != TreatmentStatus.APPOINTED) {
			throw new IllegalStateException("잘못된 예약입니다.");
		}
		this.setStatus(TreatmentStatus.CANCELED);
	}

	public void treat() {
		if (this.getStatus() != TreatmentStatus.APPOINTED) {
			throw new IllegalStateException("잘못된 예약입니다.");
		}
		this.setStatus(TreatmentStatus.TREATED);
	}
}
