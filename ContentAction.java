package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;

public class ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		int num = Integer.parseInt(req.getParameter("num"));
		//���������� ��������
		   
		//DAO���.. �ν��Ͻ�������
		BoardDAO dao = BoardDAO.getInstance();
		
		//�ش� �ν��Ͻ����� �ش�Ǵ� �޼ҵ� ����
		BoardDTO dto = dao.getArticle(num);
		
		//setAttribute�� ����� ��
		req.setAttribute("dto",dto);
		
		return "/board2/content.jsp";
	}

}
