package exercise.onemo.hospitalappointment.service;

import exercise.onemo.hospitalappointment.domain.Doctor;
import exercise.onemo.hospitalappointment.domain.Patient;
import exercise.onemo.hospitalappointment.domain.Treatment;
import exercise.onemo.hospitalappointment.domain.TreatmentStatus;
import exercise.onemo.hospitalappointment.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TreatmentService {

	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final TreatmentRepository treatmentRepository;
	private final HospitalRepository hospitalRepository;

	@Transactional
	public Long makeTreatment(Long patientId, Long doctorId, TreatmentStatus status, LocalDateTime time) {
		Patient patient = patientRepository.findOne(patientId);
		Doctor doctor = doctorRepository.findOne(doctorId);

		validateTime(time, doctor, status);
		if (status == TreatmentStatus.CANCELED) throw new RuntimeException("뭔가 잘못됐어요.");
		Treatment treatment = Treatment.createTreatment(patient, doctor, status, time);
		treatmentRepository.save(treatment);
		return treatment.getId();
	}

	@Transactional
	public void treatAppointment(Long treatmentId) {
		Treatment treatment = treatmentRepository.findOne(treatmentId);
		treatment.treat();
	}

	public List<Treatment> findTreatments(TreatmentSearch treatmentSearch) {
		return treatmentRepository.findAllByCriteria(treatmentSearch);
	}

	@Transactional
	public void cancelAppointment(Long treatmentId) {
		Treatment treatment = treatmentRepository.findOne(treatmentId);
		treatment.cancel();
	}

	private void validateTime(LocalDateTime time, Doctor doctor, TreatmentStatus status) {
		List<Treatment> findTreatments = treatmentRepository.findByDoctorAndTime(doctor, time);
		if (!findTreatments.isEmpty()) {
			throw new RuntimeException("이미 예약된 시간입니다.");
		}

		LocalTime openingTime = doctor.getHospital().getOpeningTime();
		LocalTime closingTime = doctor.getHospital().getClosingTime();

		if (status == TreatmentStatus.APPOINTED)
			if (time.isBefore(LocalDateTime.now()))
				throw new RuntimeException("현재 시각 이전에 예약을 생성할 수 없습니다.");
		if (status == TreatmentStatus.TREATED)
			if (time.isAfter(LocalDateTime.now()))
				throw new RuntimeException("진료 기록을 미리 생성할 수 없습니다.");

		if (openingTime.equals(closingTime)) return;

		if (openingTime.isBefore(closingTime)) {
			if (openingTime.isAfter(LocalTime.from(time))
				|| !closingTime.isAfter(LocalTime.from(time)))
				throw new RuntimeException("진료 시간이 아닙니다.");
		}

		else {
			if (!(!openingTime.isAfter(LocalTime.from(time))
				|| closingTime.isAfter(LocalTime.from(time))))
				throw new RuntimeException("진료 시간이 아닙니다.");
		}
	}

}
