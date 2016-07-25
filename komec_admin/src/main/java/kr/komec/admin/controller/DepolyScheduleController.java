package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.DeploySchedule;
import kr.komec.admin.repository.DeployScheduleRepository;

@RestController
public class DepolyScheduleController {

	@Autowired
	private DeployScheduleRepository deployScheduleRepository;
	
	
	@RequestMapping(value = "/depolySchedule/save", method = RequestMethod.POST)
	public DeploySchedule save(@RequestBody DeploySchedule depolySchedule) {
		depolySchedule = deployScheduleRepository.save(depolySchedule);
		return depolySchedule;
	}
	
	
	@RequestMapping(value = "/depolySchedule/getDeployScheduleList", method = RequestMethod.GET)
	public List<DeploySchedule> getDeployScheduleList() {
		return deployScheduleRepository.findAll();
	}
	
	@RequestMapping(value = "/depolySchedule/del", method = RequestMethod.POST)
	public String del(@RequestBody DeploySchedule depolySchedule) {
		deployScheduleRepository.delete(depolySchedule);
		return "{}";
	}
	
}
