package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.GroupManager;
import kr.komec.admin.repository.GroupRepository;

@RestController
public class GroupController {

	@Autowired
	private GroupRepository groupRepository;
	
	@RequestMapping(value = "/group/save", method = RequestMethod.POST)
	public GroupManager save(@RequestBody GroupManager groupManager) {
		groupRepository.save(groupManager);
		return groupManager;
	}
	
	@RequestMapping(value = "/group/getGroupList", method = RequestMethod.GET)
	public List<GroupManager> getGroupList() {
		return groupRepository.findAll();
	}
	
	@RequestMapping(value = "/group/del", method = RequestMethod.POST)
	public String del(@RequestBody GroupManager groupManager) {
		groupRepository.delete(groupManager);
		return "{}";
	}
	
}
