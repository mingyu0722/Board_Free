package kr.co.blockcom.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.management.RuntimeErrorException;

import org.springframework.web.multipart.MultipartFile;

public class FileHandler {
	
	private static final String SAVE_PATH = "C:\\upload";
	private MultipartFile file;
	
	public FileHandler(MultipartFile file) {
		// TODO Auto-generated constructor stub
		this.file = file;
	}
	
	public String uploadFile() {
		String url = null;
		
		try {
			String originalFileName = file.getOriginalFilename();
			String extractName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
			int size = (int)file.getSize();
			
			String saveFileName = genSaveFileName(extractName);
			
			writeFile(file, saveFileName);
			
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return url;
	}
	
	private String genSaveFileName(String extName) {
	    String fileName = "";
	     
	    Calendar calendar = Calendar.getInstance();
	    fileName += calendar.get(Calendar.YEAR);
	    fileName += calendar.get(Calendar.MONTH);
	    fileName += calendar.get(Calendar.DATE);
	    fileName += calendar.get(Calendar.HOUR);
	    fileName += calendar.get(Calendar.MINUTE);
	    fileName += calendar.get(Calendar.SECOND);
	    fileName += calendar.get(Calendar.MILLISECOND);
	    fileName += extName;
	     
	    return fileName;
	  }

	private boolean writeFile(MultipartFile file, String saveFileName) throws IOException{
		boolean result = false;

		byte[] data = file.getBytes();
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
		fos.write(data);
		fos.close();
 
		return result;
}

}
