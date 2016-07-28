package kr.komec.admin.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysema.query.types.Predicate;

import kr.komec.admin.domain.entity.BoardCategory;
import kr.komec.admin.domain.entity.BoardConfig;
import kr.komec.admin.domain.entity.QBoardConfig;
import kr.komec.admin.repository.BoardCategoryRepository;
import kr.komec.admin.repository.BoardConfigRepository;

@RestController
public class BoardConfigController {

	@Autowired
	private BoardConfigRepository boardConfigRepository;
	
	@Autowired
	private BoardCategoryRepository boardCategoryRepository;
	
	@RequestMapping(value = "/boardConfig/save", method = RequestMethod.POST)
	@Transactional
	public BoardConfig save(@RequestBody BoardConfig boardConfig) {
		boardConfigRepository.save(boardConfig);
		boardCategoryRepository.deleteByBoardConfigId(boardConfig.getId());
		List<BoardCategory> boardCategorys = boardConfig.getBoardCategorys();
		if (boardCategorys != null && boardCategorys.size() > 0) {
			for (BoardCategory boardCategory : boardCategorys) {
				boardCategoryRepository.save(boardCategory);
			}
		}
		return boardConfig;
	}
	
	@RequestMapping(value = "/boardConfig/getBoardConfigList", method = RequestMethod.GET)
	public List<BoardConfig> getBoardConfigList() {
		List<BoardConfig> boardConfigList = boardConfigRepository.findAll();
		for (BoardConfig boardConfig : boardConfigList) {
			boardConfig.setBoardCategorys(getBoardCategoryList(boardConfig.getId()));
		}
		return boardConfigList;
	}
	
	
	@RequestMapping(value = "/boardConfig/del", method = RequestMethod.POST)
	public String del(@RequestBody BoardConfig boardConfig) {
		boardConfigRepository.delete(boardConfig);
		return "{}";
	}
	
	@RequestMapping(value = "/boardConfig/getBoardCategoryList", method = RequestMethod.GET)
	public List<BoardCategory> getBoardCategoryList(long id) {
		return boardCategoryRepository.findByBoardConfigId(id);
	}
	
	@RequestMapping(value = "/boardConfig/getBoardConfigListForLike", method = RequestMethod.GET)
	public Iterable<BoardConfig> getBoardConfigListForLike(String filter) {
		
		QBoardConfig qBoardConfig = QBoardConfig.boardConfig;
		Predicate predicate = qBoardConfig.name.like(filter + "%").or(qBoardConfig.remarks.like(filter + "%"));
		//BooleanExpression nameLike = boardConfig.name.like(filter);
		//BooleanExpression remarksLike = boardConfig.remarks.like(filter);
		return boardConfigRepository.findAll(predicate);
	}
}
