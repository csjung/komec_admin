package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.History;
import kr.komec.admin.repository.HistoryRepository;

@RestController
public class HistoryController {

	@Autowired
	private HistoryRepository historyRepository;
	
	@RequestMapping(value = "/history/save", method = RequestMethod.POST)
	public History save(@RequestBody History history) {
		historyRepository.save(history);
		return history;
	}
	
	@RequestMapping(value = "/history/getHistoryList", method = RequestMethod.GET)
	public List<History> getHistoryList() {
		return historyRepository.findAllByOrderByYearDescMonthDesc();
	}
	
	@RequestMapping(value = "/history/del", method = RequestMethod.POST)
	public String del(@RequestBody History history) {
		historyRepository.delete(history);
		return "{}";
	}
	
}
