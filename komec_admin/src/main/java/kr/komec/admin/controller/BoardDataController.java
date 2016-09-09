package kr.komec.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.BoardData;
import kr.komec.admin.repository.BoardDataRepository;

@RestController
public class BoardDataController {

	@Autowired
	private BoardDataRepository boardDataRepository;
	
	@RequestMapping(value = "/boardData/save", method = RequestMethod.POST)
	public BoardData save(@RequestBody BoardData boardData) {
		boardData.setBoardCategoryId(boardData.getBoardCategory().getId());
		boardDataRepository.save(boardData);
		return boardData;
	}
	
	@RequestMapping(value = "/boardData/getBoardDataListByConfigId", method = RequestMethod.GET)
	public List<BoardData> getBoardDataListByConfigId(long id) {
		return boardDataRepository.findByBoardConfigIdOrderByIdDesc(id);
	}
	
	@RequestMapping(value = "/boardData/del", method = RequestMethod.POST)
	public String del(@RequestBody BoardData boardData) {
		boardDataRepository.delete(boardData);
		return "{}";
	}
	
}
