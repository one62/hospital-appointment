package exercise.onemo.hospitalappointment.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorForm {
	@NotEmpty(message = "이름을 입력해 주세요")
	private String name;
}
