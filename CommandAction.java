package model2.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAction {

		//��ȯ���� jsp������ ȣ�� ... String 
	
	public String requestPro(HttpServletRequest req, HttpServletResponse res) throws Throwable;
}
