package exercise.onemo.hospitalappointment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Doctor {
	@Id @GeneratedValue @Column(name = "doctor_id")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Treatment> treatments = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;
}
