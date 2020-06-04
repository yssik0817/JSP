package model2.board;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2.board.action.CommandAction;

/**
 * Servlet implementation class ControllerAction
 */
//@WebServlet("*.do")

public class ControllerAction extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    //��ɰ� Ŭ������ �� �ִ� Map���� hashMap();
	private Map<String,Object> commandMap = new HashMap<String,Object>();
	
	//������ ����Ǳ� ���� �ʱ�ȭ ����
	
	public void init(ServletConfig config) {
		//�ʿ��� Action--����Ͻ������� ���� �̸� ������� ����
		//�� �޸𸮿� �ø��鼭 ������ ����
		String props = config.getInitParameter("configProperty");
//		String props = "/model2/board/action/Command.properties";
		System.out.println(config);
		Properties pr = new Properties();
		FileInputStream f = null;
		try {
			//���Ϸ� ����
			f=new FileInputStream(props);
			//�� ������ Ư���� properties��(=�� �������� ������ Ű, �������� ��)
			pr.load(f);
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(f!=null)
				try {
					f.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		//properties�� �о
		Iterator<Object> key=pr.keySet().iterator();
		//Ű�� ��ɿ� �����ϰ� value�� �ִ� ���� ���Ϸ� �����ؼ� �а��ϱ�
		//�ش�Ǵ� Ŭ������ ���뿡 �޼ҵ尡 �־ �װ� ó���ϴ� ��� ����
		while(key.hasNext()) {
			String command = (String)key.next();
			String className = pr.getProperty(command);
			try {
				Class commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				//�ش� Ű�� �ν��Ͻ��� �����ؼ� web�󿡼� �ҷ��� ����� ����.
				commandMap.put(command,commandInstance);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		requestPro(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void requestPro(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	//ListAction �� ��� Ŭ������ ���� �������� �̿��ؼ�
	String view = null; //�ش�Ǵ� Action���� ���޵�	
	
	CommandAction com=null;
	try {
		String command= request.getRequestURI();
		System.out.println(command);
		System.out.println(request.getContextPath());
		if(command.indexOf(request.getContextPath())==0) {
			command=command.substring(request.getContextPath().length());
			System.out.println(command);
		}

		com=(CommandAction)commandMap.get(command);
		//com reqeuestPro ����
		
			view=com.requestPro(request, response);
		} catch (Throwable e) {
			
			e.printStackTrace();
		}
		
		//forward view�� 
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
}
