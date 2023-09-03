package exercise.onemo.hospitalappointment.repository;

import exercise.onemo.hospitalappointment.domain.TreatmentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TreatmentSearch {
	private String patientName;
	private String doctorName;
	private TreatmentStatus treatmentStatus;
}
