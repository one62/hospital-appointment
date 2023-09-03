package exercise.onemo.hospitalappointment.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
public class HospitalForm {
	@NotEmpty(message = "병원 이름을 입력해 주세요")
	private String name;

	@NotEmpty(message = "병원 주소를 입력해 주세요")
	private String address;

	@NotEmpty(message = "진료 과목을 입력해 주세요")
	private String department;

	@NotNull(message = "진료 시작 시간을 입력해 주세요")
	private LocalTime openingTime;

	@NotNull(message = "진료 종료 시간을 입력해 주세요")
	private LocalTime closingTime;
}
