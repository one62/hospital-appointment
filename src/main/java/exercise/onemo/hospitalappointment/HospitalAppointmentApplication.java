package exercise.onemo.hospitalappointment;

import exercise.onemo.hospitalappointment.domain.Doctor;
import exercise.onemo.hospitalappointment.domain.Hospital;
import exercise.onemo.hospitalappointment.domain.Patient;
import exercise.onemo.hospitalappointment.domain.Sex;
import exercise.onemo.hospitalappointment.service.DoctorService;
import exercise.onemo.hospitalappointment.service.HospitalService;
import exercise.onemo.hospitalappointment.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@SpringBootApplication
public class HospitalAppointmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(HospitalAppointmentApplication.class, args);
	}
}

@Component
@RequiredArgsConstructor
class init {
	private final HospitalService hospitalService;
	private final DoctorService doctorService;
	private final PatientService patientService;
	@EventListener(ApplicationReadyEvent.class)
	void set() {
		Hospital hospital1 = new Hospital();
		hospital1.setName("병원 예시입니다.");
		hospital1.setAddress("병원 주소입니다.");
		hospital1.setDepartment("진료 과목입니다.");
		hospital1.setOpeningTime(LocalTime.MIN);
		hospital1.setClosingTime(LocalTime.MAX);
		hospitalService.join(hospital1);

		Doctor doctor1 = new Doctor();
		doctor1.setName("의사1");
		doctorService.join(1L, doctor1);
		Doctor doctor2 = new Doctor();
		doctor2.setName("의사2");
		doctorService.join(1L, doctor2);

		Patient patient1 = new Patient();
		patient1.setName("환자1");
		patient1.setAge(99);
		patient1.setSex(Sex.ETC);
		patientService.join(patient1);
	}
}