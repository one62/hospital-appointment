package exercise.onemo.hospitalappointment.controller;

import exercise.onemo.hospitalappointment.domain.Hospital;
import exercise.onemo.hospitalappointment.service.DoctorService;
import exercise.onemo.hospitalappointment.service.HospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HospitalController {

	private final HospitalService hospitalService;
	private final DoctorService doctorService;

	@GetMapping("/hospitals/new")
	public String createForm(Model model) {
		model.addAttribute("hospitalForm", new HospitalForm());
		return "hospitals/createHospitalForm";
	}

	@PostMapping("/hospitals/new")
	public String create(@Valid HospitalForm form, BindingResult result) {
		if (result.hasErrors()) return "hospitals/createHospitalForm";

		Hospital hospital = new Hospital();
		hospital.setName(form.getName());
		hospital.setAddress(form.getAddress());
		hospital.setDepartment(form.getDepartment());
		hospital.setOpeningTime(form.getOpeningTime());
		hospital.setClosingTime(form.getClosingTime());

		hospitalService.join(hospital);
		return "redirect:/";
	}

	@GetMapping("/hospitals")
	public String hospitalList(Model model) {
		List<Hospital> hospitals = hospitalService.findHospitals();
		model.addAttribute("hospitals", hospitals);
		return "hospitals/hospitalList";
	}

}
