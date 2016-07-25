package kr.komec.admin.controller;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.domain.entity.ContentHist;
import kr.komec.admin.domain.entity.Contents;
import kr.komec.admin.repository.ContentHistRepository;
import kr.komec.admin.repository.ContentsRepository;


@RestController
public class ContentsController {

	@Autowired
	private ContentsRepository contentsRepository;
	
	@Autowired
	private ContentHistRepository contentHistRepository;
	
	// 즉시배포
	@RequestMapping(value = "/contents/save", method = RequestMethod.POST)
	public ContentHist save(@RequestBody ContentHist contentHist) {
		contentHist.setUpDate(new Date());
		Contents contents = new Contents();
	    // 최초 등록시 콘텐츠 등록
	    if (contentHist.getContentsId() == 0) {
	    	contents.setContent(contentHist.getContent());
	    	contentsRepository.save(contents);
	    	contentHist.setContentsId(contents.getId());
	    } else {
	    	contents.setId(contentHist.getContentsId()); 
	    	contents.setContent(contentHist.getContent());
	    	contentsRepository.save(contents);
	    }
		return contentHistRepository.save(contentHist);
	}
	
	// 예약배포
	@RequestMapping(value = "/contents/saveSchedule", method = RequestMethod.POST)
	public ContentHist saveSchedule(@RequestBody ContentHist contentHist) {
		contentHist.setUpDate(new Date());
		return contentHistRepository.save(contentHist);
	}
	
	@RequestMapping(value = "/contents/getContentHistList", method = RequestMethod.GET)
	public List<ContentHist> getContentHistList(long contentsId) {
		return contentHistRepository.findByContentsIdOrderByIdDesc(contentsId);
	}
	
	@RequestMapping(value = "/contents/del", method = RequestMethod.POST)
	public String del(@RequestBody Contents contents) {
		contentsRepository.delete(contents);
		return "{}";
	}
	
}
