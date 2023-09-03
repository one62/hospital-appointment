package exercise.onemo.hospitalappointment.controller;

import exercise.onemo.hospitalappointment.domain.Doctor;
import exercise.onemo.hospitalappointment.domain.Patient;
import exercise.onemo.hospitalappointment.domain.Treatment;
import exercise.onemo.hospitalappointment.domain.TreatmentStatus;
import exercise.onemo.hospitalappointment.repository.TreatmentSearch;
import exercise.onemo.hospitalappointment.service.DoctorService;
import exercise.onemo.hospitalappointment.service.PatientService;
import exercise.onemo.hospitalappointment.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TreatmentController {

	private final TreatmentService treatmentService;
	private final PatientService patientService;
	private final DoctorService doctorService;

	@GetMapping("/treatment/new")
	public String createForm(Model model) {
		List<Patient> patients = patientService.findPatients();
		List<Doctor> doctors = doctorService.findDoctors();

		model.addAttribute("patients", patients);
		model.addAttribute("doctors", doctors);
		return "treatment/treatmentForm";
	}

	@PostMapping("/treatment/new")
	public String appoint(@RequestParam("patientId") Long patientId
		, @RequestParam("doctorId") Long doctorId
		, @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime time
		, @RequestParam("status") String status1) {

		TreatmentStatus status = TreatmentStatus.valueOf(status1);
		treatmentService.makeTreatment(patientId, doctorId, status, time);
		return "redirect:/";
	}

	@GetMapping("/treatments")
	public String treatmentList(@ModelAttribute("treatmentSearch") TreatmentSearch treatmentSearch, Model model) {
		List<Treatment> treatments = treatmentService.findTreatments(treatmentSearch);
		model.addAttribute("treatments", treatments);

		return "treatment/treatmentList";
	}

	@PostMapping("/treatments/{treatmentId}/cancel")
	public String cancelAppointment(@PathVariable("treatmentId") Long treatmentId) {
		treatmentService.cancelAppointment(treatmentId);
		return "redirect:/treatments";
	}
	@PostMapping("/treatments/{treatmentId}/treat")
	public String treatAppointment(@PathVariable("treatmentId") Long treatmentId) {
		treatmentService.treatAppointment(treatmentId);
		return "redirect:/treatments";
	}
}
