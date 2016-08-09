package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.BannerZone;
import kr.komec.admin.repository.BannerZoneRepository;

@RestController
public class BannerZoneController {

	@Autowired
	private BannerZoneRepository bannerZoneRepository;
	
	@RequestMapping(value = "/bannerZone/save", method = RequestMethod.POST)
	public BannerZone save(@RequestBody BannerZone bannerZone) {
		bannerZoneRepository.save(bannerZone);
		return bannerZone;
	}
	
	@RequestMapping(value = "/bannerZone/getBannerZone", method = RequestMethod.GET)
	public BannerZone getBannerZone(long id) {
		return bannerZoneRepository.findOne(id);
	}
	
	@RequestMapping(value = "/bannerZone/getBannerZoneList", method = RequestMethod.GET)
	public List<BannerZone> getBannerZoneList() {
		return bannerZoneRepository.findAll();
	}
	
	@RequestMapping(value = "/bannerZone/del", method = RequestMethod.POST)
	public String del(@RequestBody BannerZone bannerZone) {
		bannerZoneRepository.delete(bannerZone);
		return "{}";
	}
	
}
