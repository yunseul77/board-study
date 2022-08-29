package kr.co.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.dao.BoardDAO;
import kr.co.util.FileUtils;
import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Inject
	private BoardDAO dao;
	
	//게시글 작성 + 파일 첨부 
	@Override
	public void write(BoardVO boardVO, MultipartHttpServletRequest ysRequest) throws Exception {
		dao.write(boardVO);
		
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(boardVO, ysRequest);
		int size = list.size();
		for(int i=0; i<size; i++) {
			dao.insertFile(list.get(i));
		}
	}
	
	//게시물 목록 조회
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		
		return dao.list(scri);
	}
	
	//게시물 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		
		return dao.listCount(scri);
	}
	//게시물 조회
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BoardVO read(int bno) throws Exception {
			dao.boardHit(bno);
		return dao.read(bno);
	}
	
	//게시물 수정 + 첨부파일 수정
	@Override
	public void update(BoardVO boardVO,
					   String[] files,
					   String[] fileNames,
					   MultipartHttpServletRequest ysRequest) throws Exception {
		
		dao.update(boardVO);
		
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(boardVO, files, fileNames, ysRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i = 0; i<size; i++) {
			if(tempMap.get("IS_NEW").equals("Y")) {
				dao.insertFile(tempMap);
			}else {
				dao.updateFile(tempMap);
			}
		}
	}
	
	//게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		
		dao.delete(bno);
	}
	
	//첨부파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception {
		
		return dao.selectFileList(bno);
	}
	
	//첨부파일 다운
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		
		return dao.selectFileInfo(map);
	}
}
