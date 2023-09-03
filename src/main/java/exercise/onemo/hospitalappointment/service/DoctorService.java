package exercise.onemo.hospitalappointment.service;

import exercise.onemo.hospitalappointment.domain.Doctor;
import exercise.onemo.hospitalappointment.repository.DoctorRepository;
import exercise.onemo.hospitalappointment.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DoctorService {

	private final DoctorRepository doctorRepository;
	private final HospitalRepository hospitalRepository;

	@Transactional
	public Long join(Long hospitalId, Doctor doctor) {
		validateDuplicateDoctor(doctor);
		doctor.setHospital(hospitalRepository.findOne(hospitalId));
		doctorRepository.save(doctor);
		return doctor.getId();
	}

	public List<Doctor> findDoctors() {
		return doctorRepository.findAll();
	}

	public Doctor findOne(Long doctorId) {
		return doctorRepository.findOne(doctorId);
	}

	private void validateDuplicateDoctor(Doctor doctor) {
		List<Doctor> findDoctors = doctorRepository.findByName(doctor.getName());
		if (!findDoctors.isEmpty()) {
			throw new IllegalStateException("이름이 같은 의사가 있습니다.");
		}
	}

	public List<Doctor> findDoctorsByHospital(Long id) {
		return doctorRepository.findDoctorsByHospital(id);
	}
}
