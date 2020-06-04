package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		int num = Integer.parseInt(req.getParameter("num"));
		//실제데이터 가져오기
		   
		//DAO사용.. 인스턴스얻어오기
		BoardDAO dao = BoardDAO.getInstance();
		
		//해당 인스턴스에서 해당되는 메소드 실행
		BoardDTO dto = dao.getArticle(num);
		
		//setAttribute를 해줘야 됨
		req.setAttribute("dto",dto);
		
		return "/board2/content.jsp";
	}

}
