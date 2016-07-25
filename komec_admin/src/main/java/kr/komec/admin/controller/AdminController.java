package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.AdminManager;
import kr.komec.admin.repository.AdminManagerRepository;

@RestController
public class AdminController {

	@Autowired
	private AdminManagerRepository adminRepository;
	
	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)
	public AdminManager save(@RequestBody AdminManager adminManager) {
		adminManager = adminRepository.save(adminManager);
		return adminManager;
	}
	
	@RequestMapping(value = "/admin/getAdminManagerList", method = RequestMethod.GET)
	public List<AdminManager> getAdminManagerList() {
		return adminRepository.findAll();
	}
	
	@RequestMapping(value = "/admin/getAdminByUserId", method = RequestMethod.GET)
	public List<AdminManager> getAdminByUserId(@RequestBody AdminManager adminManager) {
		return adminRepository.findByUserId(adminManager.getUserId());
	}
	
	@RequestMapping(value = "/admin/del", method = RequestMethod.POST)
	public String del(@RequestBody AdminManager adminManager) {
		adminRepository.delete(adminManager);
		return "{}";
	}
	
}
