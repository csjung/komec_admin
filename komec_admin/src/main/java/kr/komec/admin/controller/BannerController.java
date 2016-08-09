package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.Banner;
import kr.komec.admin.repository.BannerRepository;

@RestController
public class BannerController {

	@Autowired
	private BannerRepository bannerRepository;
	
	@RequestMapping(value = "/banner/save", method = RequestMethod.POST)
	public Banner save(@RequestBody Banner banner) {
	    if (banner.getSort() == 0) {
		    Long sort = bannerRepository.findByMaxSort(banner.getBannerZoneId());
		    if (sort == null) {
		    	banner.setSort(1);
		    } else {
		    	banner.setSort(sort + 1);
		    }
	    }
		bannerRepository.save(banner);
		return banner;
	}
	
	@RequestMapping(value = "/banner/getBannerList", method = RequestMethod.GET)
	public List<Banner> getBannerList(long bannerZoneId) {
		return bannerRepository.findByBannerZoneIdOrderBySortAsc(bannerZoneId);
	}
	
	@RequestMapping(value = "/banner/del", method = RequestMethod.POST)
	public String del(@RequestBody Banner banner) {
		bannerRepository.delete(banner);
		return "{}";
	}
	
}
