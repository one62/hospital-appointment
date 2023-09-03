package exercise.onemo.hospitalappointment.service;

import exercise.onemo.hospitalappointment.domain.Hospital;
import exercise.onemo.hospitalappointment.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HospitalService {

	private final HospitalRepository hospitalRepository;

	@Transactional
	public Long join(Hospital hospital) {
		validateDuplicateHospital(hospital);
		hospitalRepository.save(hospital);
		return hospital.getId();
	}

	public List<Hospital> findHospitals() {
		return hospitalRepository.findAll();
	}

	public Hospital findOne(Long hospitalId) {
		return hospitalRepository.findOne(hospitalId);
	}

	private void validateDuplicateHospital(Hospital hospital) {
		List<Hospital> findHospitals = hospitalRepository.findByName(hospital.getName());
		if (!findHospitals.isEmpty()) {
			throw new IllegalStateException("이름이 같은 병원이 있습니다.");
		}
	}


}
