package exercise.onemo.hospitalappointment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Patient {
	@Id @GeneratedValue @Column(name = "patient_id")
	private Long id;

	private String name;

	private int age;

	@Enumerated(EnumType.STRING)
	private Sex sex;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	private List<Treatment> treatments = new ArrayList<>();
}
