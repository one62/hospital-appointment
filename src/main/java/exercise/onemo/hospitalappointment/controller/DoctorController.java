package exercise.onemo.hospitalappointment.controller;

import exercise.onemo.hospitalappointment.domain.Doctor;
import exercise.onemo.hospitalappointment.domain.Hospital;
import exercise.onemo.hospitalappointment.service.DoctorService;
import exercise.onemo.hospitalappointment.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {
	private final DoctorService doctorService;
	private final HospitalService hospitalService;

	@GetMapping("doctors/new")
	public String createForm(Model model) {
		List<Hospital> hospitals = hospitalService.findHospitals();
		model.addAttribute("hospitals", hospitals);
		return "doctors/createDoctorForm";
	}

	@PostMapping("doctors/new")
	public String create(@RequestParam("hospitalId")Long hospitalId, @RequestParam("doctorName")String doctorName) {
		Doctor doctor = new Doctor();
		doctor.setName(doctorName);
		doctorService.join(hospitalId, doctor);
		return "redirect:/";
	}
}
