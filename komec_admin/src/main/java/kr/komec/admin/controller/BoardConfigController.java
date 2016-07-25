package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.BoardConfig;
import kr.komec.admin.repository.BoardConfigRepository;

@RestController
public class BoardConfigController {

	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@RequestMapping(value = "/boardConfig/save", method = RequestMethod.POST)
	public BoardConfig save(@RequestBody BoardConfig boardConfig) {
		boardConfig = boardConfigRepository.save(boardConfig);
		return boardConfig;
	}
	
	@RequestMapping(value = "/boardConfig/getBoardConfigList", method = RequestMethod.GET)
	public List<BoardConfig> getBoardConfigList() {
		return boardConfigRepository.findAll();
	}
	
	
	@RequestMapping(value = "/boardConfig/del", method = RequestMethod.POST)
	public String del(@RequestBody BoardConfig boardConfig) {
		boardConfigRepository.delete(boardConfig);
		return "{}";
	}
}
