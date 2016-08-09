package kr.komec.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.komec.admin.config.ServerConfig;

@RestController
public class CommonController {

	@Autowired
	private ServerConfig serverConfig;


	/** 파일 업로드 **/
	@RequestMapping(value="/common/UploadFile")
	public @ResponseBody String UploadFile(
			@RequestParam("file") MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filename ="";
		try {
			File path = new File(serverConfig.getWebfileDir() + "/" + request.getParameter("dir"));
			if (!path.exists()) path.mkdirs();
			
			for(int i=0; i<files.length; i++) {
				File saveFile = null;
				filename = String.valueOf(System.currentTimeMillis());
				saveFile = new File(path, filename); 
				files[i].transferTo(saveFile);
			}
		} catch (IOException e) {
			throw e;
		}
		return filename;
	}
	
	@ResponseBody
    @RequestMapping("/ckImageUploadBrowserTab.do")
    public void ckImageUploadBrowserTab(@RequestParam("upload") MultipartFile[] files,
    		HttpServletRequest request, HttpServletResponse response)  throws Exception {
    
		String callback = request.getParameter("CKEditorFuncNum");
		PrintWriter printWriter = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
		try {
			File path = new File(serverConfig.getWebfileDir() + "/ckeditorimg");
			if (!path.exists()) path.mkdirs();
			
			printWriter = response.getWriter();
			String filename = "";
			for(int i=0; i<files.length; i++) {
				File saveFile = null;
				String ext = files[i].getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");
				filename = String.valueOf(System.currentTimeMillis()) + "." + ext;
				saveFile = new File(path, filename); 
				files[i].transferTo(saveFile);
			}			
			
			printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
                    + callback
                    + ",'"
                    + "/getCkImage?filename=" + filename
                    + "','이미지를 업로드 하였습니다.'"
                    + ")</script>");
            printWriter.flush();
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	/** 파일 다운로드 **/
	@RequestMapping(value="/getCkImage")
	public void downloadFile(
			@RequestParam("filename") String filename,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		File file = new File(serverConfig.getWebfileDir() + "/ckeditorimg", filename);
		
		response.setHeader("Content-Length", Long.toString(file.length()));
		//WebUtil.setContentDisposition(proBusinessFilesVO.getFilename(), request, response);
	    FileInputStream inputStream = new FileInputStream(file);
	    IOUtils.copyLarge(inputStream, response.getOutputStream());
	    inputStream.close();
	}
	
}
