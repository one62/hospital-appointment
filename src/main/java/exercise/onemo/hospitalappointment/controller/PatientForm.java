package exercise.onemo.hospitalappointment.controller;

import exercise.onemo.hospitalappointment.domain.Sex;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatientForm {

	@NotEmpty(message = "이름을 입력해 주세요.")
	private String name;

	private Sex sex;

	private int age;
}
