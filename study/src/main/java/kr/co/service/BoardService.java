package kr.co.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.vo.BoardVO;
import kr.co.vo.SearchCriteria;

public interface BoardService {
	
	//게시글 작성
	public void write(BoardVO boardVO, MultipartHttpServletRequest ysRequest) throws Exception;
	
	//게시물 목록 조회
	public List<BoardVO> list(SearchCriteria scri) throws Exception;
		
	//게시물 총 갯수
	public int listCount(SearchCriteria scri) throws Exception;
		
	//게시물 조회
	public BoardVO read(int bno) throws Exception;
	
	//게시물 수정 + 첨부파일 수정
	public void update(BoardVO boardVO,
					   String[] files,
					   String[] fileNames,
					   MultipartHttpServletRequest ysRequest) throws Exception;
	
	//게시물 삭제
	public void delete(int bno) throws Exception;
	
	//첨부파일 조회
	public List<Map<String, Object>> selectFileList(int bno) throws Exception;

	//첨부파일 다운
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;

	//게시글 조회수
	public void boardHit(int bno) throws Exception;
}
