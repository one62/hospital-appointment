package exercise.onemo.hospitalappointment.repository;

import exercise.onemo.hospitalappointment.domain.Doctor;
import exercise.onemo.hospitalappointment.domain.Patient;
import exercise.onemo.hospitalappointment.domain.Treatment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TreatmentRepository {
	private final EntityManager em;

	public void save(Treatment treatment) {
		em.persist(treatment);
	}

	public Treatment findOne(Long id) {
		return em.find(Treatment.class, id);
	}

	public List<Treatment> findByDoctorAndTime(Doctor doctor, LocalDateTime time) {
		return em.createQuery("select t from Treatment t where t.doctor = :doctor and t.time = :time")
				.setParameter("doctor", doctor)
				.setParameter("time", time)
				.getResultList();
	}

	public List<Treatment> findAllByCriteria(TreatmentSearch treatmentSearch) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Treatment> cq = cb.createQuery(Treatment.class);
		Root<Treatment> o = cq.from(Treatment.class);
		Join<Order, Patient> p = o.join("patient", JoinType.INNER); //회원과 조인
		Join<Order, Doctor> d = o.join("doctor", JoinType.INNER); //회원과 조인
		List<Predicate> criteria = new ArrayList<>();
		//주문 상태 검색
		if (treatmentSearch.getTreatmentStatus() != null) {
			Predicate status = cb.equal(o.get("status"),
					treatmentSearch.getTreatmentStatus());
			criteria.add(status);
		}
		//회원 이름 검색
		if (StringUtils.hasText(treatmentSearch.getPatientName())) {
			Predicate patientName =
					cb.like(p.<String>get("name"), "%" +
							treatmentSearch.getPatientName() + "%");
			criteria.add(patientName);
		}
		if (StringUtils.hasText(treatmentSearch.getDoctorName())) {
			Predicate doctorName =
					cb.like(d.<String>get("name"), "%" +
							treatmentSearch.getDoctorName() + "%");
			criteria.add(doctorName);
		}
		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		TypedQuery<Treatment> query = em.createQuery(cq).setMaxResults(1000); //최대1000건
		return query.getResultList();
	}
}
