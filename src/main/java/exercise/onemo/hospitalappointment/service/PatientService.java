package exercise.onemo.hospitalappointment.service;

import exercise.onemo.hospitalappointment.domain.Patient;
import exercise.onemo.hospitalappointment.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientService {
	private final PatientRepository patientRepository;

	@Transactional
	public Long join(Patient patient) {
		validateDuplicatePatient(patient);
		patientRepository.save(patient);
		return patient.getId();
	}

	private void validateDuplicatePatient(Patient patient) {
		List<Patient> findPatients = patientRepository.findByName(patient.getName());
		if (!findPatients.isEmpty()) {
			throw new IllegalStateException("이름이 같은 환자가 있습니다.");
		}
	}

	public List<Patient> findPatients() {
		return patientRepository.findAll();
	}

	public Patient findOne(Long patientId) {
		return patientRepository.findOne(patientId);
	}
}
