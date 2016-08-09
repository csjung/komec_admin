package kr.komec.admin.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.komec.admin.config.ServerConfig;
import kr.komec.admin.domain.entity.FileInfo;
import kr.komec.admin.repository.FileInfoRepository;

@RestController
public class FileInfoController {

	@Autowired
	private ServerConfig serverConfig;
	
	@Autowired
	private FileInfoRepository fileInfoRepository;
	
	@RequestMapping(value = "/fileInfo/save", method = RequestMethod.POST)
	public FileInfo save(@RequestBody FileInfo fileInfo) {
		fileInfoRepository.save(fileInfo);
		return fileInfo;
	}
	
	@RequestMapping(value = "/fileInfo/del", method = RequestMethod.POST)
	public String del(@RequestBody FileInfo fileInfo) {
		try {
			File file = new File(serverConfig.getWebfileDir() + fileInfo.getFilePath(), fileInfo.getPhyFileName());
			file.delete();
		} catch (Exception e) {}
		fileInfoRepository.delete(fileInfo);
		return "{}";
	}
	
	@RequestMapping(value = "/fileInfo/delById", method = RequestMethod.POST)
	public String delById(long id) {
		FileInfo fileInfo = fileInfoRepository.findOne(id);
		try {
			File file = new File(serverConfig.getWebfileDir() + fileInfo.getFilePath(), fileInfo.getPhyFileName());
			file.delete();
		} catch (Exception e) {}
		fileInfoRepository.delete(fileInfo);
		return "{}";
	}
	
	/** 파일 다운로드 **/
	@RequestMapping(value="/fileInfo/downloadFile.do")
	public void downloadFile(
			@RequestParam("id") long id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileInfo fileInfo = fileInfoRepository.findOne(id);
		File file = new File(serverConfig.getWebfileDir() + fileInfo.getFilePath(), fileInfo.getPhyFileName());
		response.setHeader("Content-Length", Long.toString(file.length()));
	    FileInputStream inputStream = new FileInputStream(file);
	    IOUtils.copyLarge(inputStream, response.getOutputStream());
	    inputStream.close();
	}
	
}
