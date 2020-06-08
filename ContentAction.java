package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardDTO;
import board.PageDTO;

public class ContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		int num = Integer.parseInt(req.getParameter("num"));
		int currentPage =0;
		if(req.getParameter("currentPage")==null)
			currentPage=1;
			else {
				currentPage = Integer.parseInt(req.getParameter("currentPage"));
			}	
		int currPageBlock=0;
		if(req.getParameter("currPageBlock")==null) {
			currPageBlock=1;
			}else {
				currPageBlock = Integer.parseInt(req.getParameter("currPageBlock"));
			}
		//���� �� �޾ƿ���
			
		//���������� ��������
		PageDTO pdto = new PageDTO();
		pdto.setCurrentPage(currentPage);
		pdto.setCurrPageBlock(currPageBlock);
		
		//DAO���.. �ν��Ͻ�������
		BoardDAO dao = BoardDAO.getInstance();
		
		//�ش� �ν��Ͻ����� �ش�Ǵ� �޼ҵ� ����
		BoardDTO dto = dao.getArticle(num);
		
		//setAttribute�� ����� ��
		req.setAttribute("dto",dto);
		req.setAttribute("pdto",pdto);
		
		return "/board2/content.jsp";
	}

}
