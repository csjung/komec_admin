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
	public Menu save(@RequestBody Menu menu) {
	    if (menu.getSort() == 0) {
		Long sort = menuRepository.findByMaxSort(menu.getUpperId());
			if (sort == null) {
				menu.setSort(1);
			} else {
				menu.setSort(sort.longValue() + 1);
			}
	    }
		menu = menuRepository.save(menu);
		return menu;
	}
	
	@RequestMapping(value = "/menu/getMenuList", method = RequestMethod.GET)
	public List<Menu> getMenuList() {
		return menuRepository.findAll();
	}
	
	@RequestMapping(value = "/menu/getMenuListBySite", method = RequestMethod.GET)
	public List<Menu> getMenuListBySite(long siteId) {
		return menuRepository.findBySiteId(siteId);
	}
	
	@RequestMapping(value = "/menu/getMenuById", method = RequestMethod.GET)
	public Menu getMenuById(long id) {
		return menuRepository.findOne(id);
	}
	
	@RequestMapping(value = "/menu/del", method = RequestMethod.POST)
	public String del(@RequestBody Menu menu) {
		menuRepository.delete(menu);
		return "{}";
	}
	
}
