package kr.co.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.vo.BoardVO;

@Component("fileUtils")
public class FileUtils {
	private static final String filePath = "E:\\dev-git\\file\\";
	
	public List<Map<String, Object>> parseInsertFileInfo(BoardVO boardVO, 
			MultipartHttpServletRequest ysRequest) throws Exception{

		Iterator<String> iterator = ysRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		int bno = boardVO.getBno();
		
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		while(iterator.hasNext()) {
			multipartFile = ysRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = getRandomString() + originalFileExtension;
				
				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file);
				listMap = new HashMap<String, Object>();
				listMap.put("BNO", bno);
				listMap.put("ORG_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);
			}
		}
		return list;
	}
	
	public List<Map<String, Object>> parseUpdateFileInfo(BoardVO boardVO, String[] files, String[] fileNames, MultipartHttpServletRequest ysRequest) throws Exception{ 
		Iterator<String> iterator = ysRequest.getFileNames();
		MultipartFile multipartFile = null; 
		String originalFileName = null; 
		String originalFileExtension = null; 
		String storedFileName = null; 
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null; 
		int bno = boardVO.getBno();
		while(iterator.hasNext()){ 
			multipartFile = ysRequest.getFile(iterator.next()); 
			if(multipartFile.isEmpty() == false){ 
				originalFileName = multipartFile.getOriginalFilename(); 
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); 
				storedFileName = getRandomString() + originalFileExtension; 
				multipartFile.transferTo(new File(filePath + storedFileName)); 
				listMap = new HashMap<String,Object>();
				listMap.put("IS_NEW", "Y");
				listMap.put("BNO", bno); 
				listMap.put("ORG_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName); 
				listMap.put("FILE_SIZE", multipartFile.getSize()); 
				list.add(listMap); 
			} 
		}
		if(files != null && fileNames != null){ 
			for(int i = 0; i<fileNames.length; i++) {
					listMap = new HashMap<String,Object>();
                    listMap.put("IS_NEW", "N");
					listMap.put("FILE_NO", files[i]); 
					list.add(listMap); 
			}
		}
		return list; 
	}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
