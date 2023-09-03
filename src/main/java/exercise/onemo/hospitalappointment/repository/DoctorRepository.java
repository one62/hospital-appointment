package exercise.onemo.hospitalappointment.repository;

import exercise.onemo.hospitalappointment.domain.Doctor;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DoctorRepository {
	private final EntityManager em;

	public void save(Doctor doctor) {
		em.persist(doctor);
	}

	public Doctor findOne(Long id) {
		return em.find(Doctor.class, id);
	}

	public List<Doctor> findByName(String name) {
		return em.createQuery("select d from Doctor d where d.name = :name", Doctor.class)
				.setParameter("name", name)
				.getResultList();
	}

	public List<Doctor> findAll() {
		return em.createQuery("select d from Doctor d", Doctor.class)
				.getResultList();
	}

	public List<Doctor> findDoctorsByHospital(Long id) {
		return em.createQuery("select h.doctors from Hospital h where h.id = :id", Doctor.class)
				.setParameter("id", id)
				.getResultList();
	}

}
