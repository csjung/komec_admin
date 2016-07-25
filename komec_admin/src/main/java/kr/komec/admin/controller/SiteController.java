package kr.komec.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.Menu;
import kr.komec.admin.domain.entity.Site;
import kr.komec.admin.repository.MenuRepository;
import kr.komec.admin.repository.SiteRepository;

@RestController
public class SiteController {

	@Autowired
	private SiteRepository siteRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@RequestMapping(value = "/site/save", method = RequestMethod.POST)
	public Site save(@RequestBody Site siteManager) {
		siteManager = siteRepository.save(siteManager);
		return siteManager;
	}
	
	@RequestMapping(value = "/site/getSite", method = RequestMethod.GET)
	public List<Site> getSite(long id) {
		List<Site> list = new ArrayList<Site>();
		Site site = siteRepository.getOne(id);
		list.add(site);
		List<Menu> menus = menuRepository.findBySiteIdAndUpperIdOrderBySortAsc(id, 0);
		if (menus != null) {
			site.setMenus(menus);
		}
		return list;
	}
	
	@RequestMapping(value = "/site/getSiteList", method = RequestMethod.GET)
	public List<Site> getSiteList() {
		return siteRepository.findAll();
	}
	
	@RequestMapping(value = "/site/del", method = RequestMethod.POST)
	public String del(@RequestBody Site siteManager) {
		siteRepository.delete(siteManager);
		return "{}";
	}
	
}
