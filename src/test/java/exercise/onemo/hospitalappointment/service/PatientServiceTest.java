package exercise.onemo.hospitalappointment.service;

import exercise.onemo.hospitalappointment.domain.Patient;
import exercise.onemo.hospitalappointment.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class PatientServiceTest {
	@Autowired
	PatientService patientService;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	EntityManager em;

	@Test
	public void 중복회원() throws Exception {
		Patient patient1 = new Patient();
		patient1.setName("kim");
		Patient patient2 = new Patient();
		patient2.setName("kim");

		patientService.join(patient1);
		Assertions.assertThrows(IllegalStateException.class, () -> {
			patientService.join(patient2);
		});
	}
}
