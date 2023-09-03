package exercise.onemo.hospitalappointment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Hospital {
	@Id @GeneratedValue @Column(name = "hospital_id")
	private Long id;

	private String name;
	private String address;
	private String department;

	private LocalTime openingTime;
	private LocalTime closingTime;

	@OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
	private List<Doctor> doctors = new ArrayList<>();
}
