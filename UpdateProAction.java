package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class UpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {

		
	      //writeForm에서 보내준 데이터 모두 받기
	      BoardDTO dto = new BoardDTO();
	      //num, ref, re_step, re_level 받아야된다.
	      //writer, subject, content, passwd, attachNm,email
	      dto.setNum(Integer.parseInt(req.getParameter("num")));
	      dto.setSubject(req.getParameter("subject"));
	      dto.setContent(req.getParameter("content"));
	      dto.setPasswd(req.getParameter("passwd"));
	      dto.setEmail(req.getParameter("email"));
	      dto.setAttachNm(req.getParameter("attachNm"));

	      //DAO를 통해서 받은 데이터저장하기
	       //DAO에 대한 인스턴스 받아오기
	       BoardDAO dao = BoardDAO.getInstance();
	       //DAO에 해당 데이터 저장하는 로직을 만들고
		
				//그 로직을 사용한 후 
	       		dao.boardWrite(dto);
				//다음 페이지로 이동시킴
		
		return "/board2/updatePro.jsp";
	}

}
