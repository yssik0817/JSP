package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import common.dbutil.DBConnection;

public class BoardDAO {
   //DAO: Data Access Object
   //DB�����ؼ� ó���ϴ� �۾��� ��� ó���ϴ� ��ü
   
   /*
      DBó���ؾ� �� ���� ������ DAO �ν��Ͻ��� �޾Ƽ�
      �� �� insert, delete �� ��� �۾��� 
      ���ȭ�� ������ �ٸ� ��ü���� ȣ���ؼ� ���
   */
   
  
   
   private static BoardDAO instance = new BoardDAO();
   
   public static BoardDAO getInstance() {
      return instance;
   }
   public int boardWrite(BoardDTO dto) throws NamingException, SQLException {
      Connection conn= DBConnection.getConnection();
      int cnt=0;
      PreparedStatement pstmt=null;
      ResultSet rs= null;
      
      //�۹�ȣ�� ���� ����
      int num=dto.getNum();
      int number=0;
      int ref=dto.getRef();
      int re_step=dto.getRe_step();
      int re_level=dto.getRe_level();
         if(conn!=null) {
            String sql="";
               //���ο� �۹�ȣ �����
         //�������� �޾Ƽ� �����صΰ�  insert�� 
            sql ="select max(num)+1 num from board";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            if(rs.next()) {
               number=rs.getInt("num");
               }
           if(num==0) {
           num=number;
            ref=num;
            re_step=1;
            re_level=1;
           }else if (num!=0) {
              num=number;
              re_step+=1;
               re_level+=1;
           }
         //������ ���� ���� ����Ѵ�.
            sql="insert into board(NUM,"
            + "WRITER,SUBJECT,EMAIL,CONTENT,PASSWD,REG_DATE,READCNT,"
            + "REF,RE_STEP,RE_LEVEL,ATTACHNM )";
            sql+="values(?,?,"
            + "?,?,?,?,sysdate,0,"
            + "?, ?, ?,?)";
            
         pstmt=conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         pstmt.setString(2, dto.getWriter());
         pstmt.setString(3, dto.getSubject());
         pstmt.setString(4, dto.getEmail());
         pstmt.setString(5, dto.getContent());
         pstmt.setString(6, dto.getPasswd());
         pstmt.setInt(7, ref);   //�׷�
         pstmt.setInt(8, re_step);   //����� �ܰ�
         pstmt.setInt(9, re_level);   //����� ����
         pstmt.setString(10, dto.getAttachNm());   
         cnt = pstmt.executeUpdate();
         
         
      }
      if(rs!=null) rs.close();
      if(pstmt!=null)pstmt.close();
      if(conn!=null)conn.close();
      return cnt;
   }
   
   public List<BoardDTO> getArticles(int sRow, int pageSize) throws NamingException, SQLException{
      String sql = "select a.* from"+
             " (select rownum rr,rboard.* from"+
             "(select NUM, WRITER, SUBJECT,EMAIL, CONTENT, PASSWD, REG_DATE,"+
             " READCNT, REF, RE_STEP, RE_LEVEL, ATTACHNM "+
            "from board order by ref desc, re_step asc)rboard )a "+
             "where a.rr BETWEEN ? and ? ";
       
         Connection conn= DBConnection.getConnection();
         List<BoardDTO> articles = new ArrayList<BoardDTO>(20);
         PreparedStatement pstmt=conn.prepareStatement(sql);
         
         pstmt.setInt(1, sRow);
         pstmt.setInt(2, pageSize);
         
         ResultSet rs = pstmt.executeQuery();
         while(rs.next()){
            //�ϳ��� ������ �����ϴ� �۾�
            BoardDTO dto = new BoardDTO();
            dto.setRr(rs.getInt("rr"
                  + ""
                  + ""
                  + ""
                  + ""));
            dto.setNum(rs.getInt("num"));
            dto.setRef(rs.getInt("ref"));
            dto.setRe_step(rs.getInt("Re_step"));
            dto.setRe_level(rs.getInt("Re_level"));
            dto.setReadcnt(rs.getInt("Readcnt"));
            dto.setWriter(rs.getString("Writer"));
            dto.setSubject(rs.getString("subject"));
            dto.setContent(rs.getString("content"));
            dto.setEmail(rs.getString("email"));
            dto.setReg_date(rs.getString("reg_date"));
            dto.setPasswd(rs.getString("passwd"));
            dto.setAttachNm(rs.getString("AttachNm"));
            articles.add(dto);
            
         }
         if(rs!=null) rs.close();
         if(pstmt!=null)pstmt.close();
         if (conn!=null)conn.close();
   
         return articles;
        
   }
   
   public BoardDTO getArticle(int num) throws NamingException/* , SQLException */{
      //��ȸ�� �����ϴ� �۾�
      
      PreparedStatement pstmt = null;
      BoardDTO dto = new BoardDTO();
      ResultSet rs = null;
      
      try {
        
         Connection conn= DBConnection.getConnection();
      pstmt =conn.prepareStatement("Update board set Readcnt = Readcnt+1 where num=?") ;
      pstmt.setInt(1, num);
      
      int i= pstmt.executeUpdate();
        
      if(i>0) {
      //����Ŭ..sql
      String sql="select NUM,WRITER,SUBJECT,EMAIL,CONTENT,PASSWD"
               + ",REG_DATE,READCNT,REF,RE_STEP,RE_LEVEL, ATTACHNM"
               + " from board where num = ?";
        
      //connection ��������
         
         //���� num�� �ش�Ǵ� ������ ������� �۾�
         pstmt=conn.prepareStatement(sql);
         pstmt.setInt(1, num);
         rs=pstmt.executeQuery();
         
         if(rs.next()){
            //�ϳ��� ������ �����ϴ� �۾�
            dto.setNum(rs.getInt("num"));
            dto.setRef(rs.getInt("ref"));
            dto.setRe_step(rs.getInt("Re_step"));
            dto.setRe_level(rs.getInt("Re_level"));
            dto.setReadcnt(rs.getInt("Readcnt"));
            dto.setWriter(rs.getString("Writer"));
            dto.setSubject(rs.getString("subject"));
            dto.setContent(rs.getString("content"));
            dto.setEmail(rs.getString("email"));
            dto.setReg_date(rs.getString("reg_date"));
            dto.setPasswd(rs.getString("passwd"));
            dto.setAttachNm(rs.getString("AttachNm"));
         }
         System.out.println(dto.getRef());
      }
      if(rs!=null) rs.close();
      if(pstmt!=null)pstmt.close();
      if (conn!=null)conn.close();
      }catch(SQLException e) {
         e.printStackTrace();
      
      }
      //connection �ݱ� 
         return dto;
   }
   
   public int boardUpdate(BoardDTO dto) {
      int r= 0;
      //DBConnection
      //prepaeredstatement
      //����
      System.out.println(dto.getSubject());
       PreparedStatement pstmt = null;
       try {
          String sql = "update board set " + 
                "SUBJECT=? ," + 
                "EMAIL=? ," + 
                "CONTENT=? ," + 
                "PASSWD=? ," + 
                "ATTACHNM=? " + 
                " where num = ? ";
         Connection conn= DBConnection.getConnection();
         if(conn!=null) {
            System.out.println("���Ἲ��");
         
           pstmt = conn.prepareStatement(sql);
           pstmt.setString(1, dto.getSubject());
           pstmt.setString(2, dto.getEmail());
           pstmt.setString(3, dto.getContent());
           pstmt.setString(4, dto.getPasswd());
           pstmt.setString(5, dto.getAttachNm());
           pstmt.setInt(6, dto.getNum());
           r = pstmt.executeUpdate();}
         if(pstmt!=null)pstmt.close();
          if (conn!=null)conn.close();
      } catch (NamingException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
       
       return r;
   }
   public int deleteArticle(int num) {
      int r = 0;
      //DB����
      //���������
      //pstmt�����
      //�����ϰ�
      //return
      PreparedStatement pstmt = null;
      Connection conn = null;
       try {
         conn= DBConnection.getConnection();
         String sql = "delete from board where num = ?";
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, num);
           r = pstmt.executeUpdate();
      } catch (NamingException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
       try {
          
          if(pstmt!=null)pstmt.close();
          if (conn!=null)conn.close();
       }catch (SQLException e) {
            e.printStackTrace();
         }
      return r;
   }
   
   	public int getAllCount() throws NamingException {
   		//Conn, pstmt, rs
   		PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int allCnt=0;
        try {
            conn= DBConnection.getConnection();
            String sql="select count(num) cnt from board";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	allCnt=rs.getInt("cnt");
            }
        
          //rs, pstmt, conn �ݱ�
        	 if(rs!=null) {rs.close();}
             if(pstmt!=null){pstmt.close();};
             if(conn!=null){conn.close();};
          }catch (SQLException e) {
               e.printStackTrace();
            }
         return allCnt;
      }
	}