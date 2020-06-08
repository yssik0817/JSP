package model2.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import board.BoardDTO;
import board.PageDTO;

public class ListAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable {
		BoardDAO dao = BoardDAO.getInstance();
		PageDTO pdto = new PageDTO();
		HttpSession session= req.getSession();
		//���������� ���� �޾ƿ���, �޾ƿ� ������ ���� �������� ������
		//������������, ��������ϵ� 1�̵ȴ�.
		
		//��ü ���ڵ� ��
		int cnt = dao.getAllCount();
		pdto.setAllCount(cnt);
		
		//��ü ������ ��
		int pageCnt=cnt%pdto.getLinePerPage();
		if(pageCnt==0) {
			//���� ��ü ��������
			pdto.setAllPage(cnt/pdto.getLinePerPage());
		}else {
			//��+1�� ��ü ������ ��
			pdto.setAllPage(cnt/pdto.getLinePerPage()+1);
		}
		int currentPage=0;
		if(req.getParameter("currentPage")==null)
			currentPage=1;
			else {
				currentPage = Integer.parseInt(req.getParameter("currentPage"));
			}	
		//���� �� �޾ƿ���
		int currPageBlock=0;
		if(req.getParameter("currPageBlock")==null) {
			currPageBlock=1;
		}else {
			currPageBlock = Integer.parseInt(req.getParameter("currPageBlock"));
		}
		
		pdto.setCurrentPage(currentPage);
		pdto.setCurrPageBlock(currPageBlock);
		
		int startPage=1;
		int endPage=1;		
		//��ü ������ ���� ������ �۴�
		if(pdto.getAllPage()<pdto.getPageBlock()) {
			startPage=1;
			endPage=pdto.getAllPage();
		}else {
			//��ü ������ ���� ������ ũ��
			//���� ������ ���� ���� �ٲ�
			startPage=(currPageBlock-1)*pdto.getPageBlock()+1;
			endPage=currPageBlock*pdto.getPageBlock()>pdto.getAllPage()?
			pdto.getAllPage():
			currPageBlock*pdto.getPageBlock();
		}
		
		pdto.setStartPage(startPage);
		pdto.setEndPage(endPage);
		
		//���� ��
				int sRow=(currentPage-1)*pdto.getLinePerPage()+1;
				List<BoardDTO> list = 
						dao.getArticles(sRow, currentPage*pdto.getLinePerPage()); 	
				//view���� �� ��� �� ����
				req.setAttribute("list",list);
				//������ ������ ����
				req.setAttribute("pdto", pdto);
				return "/board2/boardList.jsp";
			}
		}
