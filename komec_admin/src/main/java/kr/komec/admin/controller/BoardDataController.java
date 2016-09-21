package kr.komec.admin.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.BoardData;
import kr.komec.admin.domain.entity.BoardFiles;
import kr.komec.admin.repository.BoardDataRepository;
import kr.komec.admin.repository.BoardFilesRepository;

@RestController
public class BoardDataController {

	@Autowired
	private BoardDataRepository boardDataRepository;
	
	@Autowired
	private BoardFilesRepository boardFilesRepository;
	
	@RequestMapping(value = "/boardData/save", method = RequestMethod.POST)
	public BoardData save(@RequestBody BoardData boardData) {
	    if (boardData.getBoardCategory() != null) {
	    	boardData.setBoardCategoryId(boardData.getBoardCategory().getId());
	    }
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
	
	@RequestMapping(value = "/boardFiles/getFiles", method = RequestMethod.GET)
	public List<BoardFiles> getBoardFiles(long id) {
		return boardFilesRepository.findByBoardDataId(id);
	}
	
	@RequestMapping(value = "/boardFiles/save", method = RequestMethod.POST)
	public BoardFiles boardFilesSave(@RequestBody BoardFiles boardFiles) {
		boardFilesRepository.save(boardFiles);
		return boardFiles;
	}
	
	@RequestMapping(value = "/boardFiles/saveList", method = RequestMethod.POST)
	@Transactional
	public String boardFilesListSave(@RequestBody List<BoardFiles> boardFiles) {
		boardFilesRepository.save(boardFiles);
		return "{}";
	}
	
	@RequestMapping(value = "/boardFiles/del", method = RequestMethod.POST)
	public String boardFilesDel(@RequestBody BoardFiles boardFiles) {
		boardFilesRepository.delete(boardFiles);
		return "{}";
	}
	
}
