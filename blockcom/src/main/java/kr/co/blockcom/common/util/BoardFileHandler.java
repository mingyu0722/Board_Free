package kr.co.blockcom.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import kr.co.blockcom.board.vo.FileVO;

public class BoardFileHandler {
	
	private MultipartFile file;
	private FileVO fvo;
	ServletContext servletContext;
	
	public BoardFileHandler(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void uploadFile(FileVO fvo, MultipartFile file) {
		String REAL_PATH = servletContext.getRealPath("/files");
		String SAVE_PATH = servletContext.getContextPath();
		try {
			String originalFileName = file.getOriginalFilename();
			String extractName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			int size = (int)file.getSize();
			
			String saveFileName = genSaveFileName(extractName);
			String realPath = REAL_PATH + "/" + saveFileName;
			String savePath = SAVE_PATH + "/files/" + saveFileName;
			writeFile(file, realPath);
			
			fvo.setFile_name(originalFileName);
			fvo.setFile_path(savePath);
			fvo.setFile_size(size);			
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String genSaveFileName(String extractName) {
	    String fileName = "";
	     
	    Calendar calendar = Calendar.getInstance();
	    
	    fileName += calendar.get(Calendar.YEAR);
	    fileName += calendar.get(Calendar.MONTH);
	    fileName += calendar.get(Calendar.DATE);
	    fileName += calendar.get(Calendar.HOUR);
	    fileName += calendar.get(Calendar.MINUTE);
	    fileName += calendar.get(Calendar.SECOND);
	    fileName += calendar.get(Calendar.MILLISECOND);
	    fileName += extractName;
	     
	    return fileName;
	  }

	private void writeFile(MultipartFile file, String savePath) throws IOException{
		boolean result = false;

		byte[] data = file.getBytes();
		FileOutputStream fos = new FileOutputStream(savePath);
		fos.write(data);
		fos.close();
	}
}
