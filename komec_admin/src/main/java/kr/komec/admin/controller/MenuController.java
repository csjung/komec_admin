package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.Menu;
import kr.komec.admin.repository.MenuRepository;

@RestController
public class MenuController {

	@Autowired
	private MenuRepository menuRepository;
	
	@RequestMapping(value = "/menu/save", method = RequestMethod.POST)
	public Menu save(@RequestBody Menu menuManager) {
	    if (menuManager.getSort() == 0) {
		Long sort = menuRepository.findByMaxSort(menuManager.getUpperId());
			if (sort == null) {
				menuManager.setSort(1);
			} else {
				menuManager.setSort(sort.longValue() + 1);
			}
	    }
		menuManager = menuRepository.save(menuManager);
		return menuManager;
	}
	
	@RequestMapping(value = "/menu/getMenuList", method = RequestMethod.GET)
	public List<Menu> getMenuList() {
		return menuRepository.findAll();
	}
	
	@RequestMapping(value = "/menu/getMenuListBySite", method = RequestMethod.GET)
	public List<Menu> getMenuListBySite(long siteId) {
		return menuRepository.findBySiteId(siteId);
	}
	
	@RequestMapping(value = "/menu/del", method = RequestMethod.POST)
	public String del(@RequestBody Menu menuManager) {
		menuRepository.delete(menuManager);
		return "{}";
	}
	
}
