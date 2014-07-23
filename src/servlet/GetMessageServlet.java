package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Message;

/**
 * 短连接方式获取消息用Servlet
 * @author Admin
 *
 */
public class GetMessageServlet extends HttpServlet {

	private static final long serialVersionUID = -4513307198186243260L;
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object obj = request.getSession().getAttribute("user");			//从Session中获取当前用户
		if(obj != null){												//如果用户存在 则读取消息

			response.setContentType("text/xml;charset=utf-8");
			PrintWriter out = response.getWriter();
			StringBuilder sb = new StringBuilder();			//xml字符串拼接对象
			sb.append("<date>");
			
			String username = obj.toString();							//获取用户名
			
			//从Session中获取消息集合
			ConcurrentLinkedQueue<Message> msglist = (ConcurrentLinkedQueue<Message>)(request.getSession().getAttribute("msglist"));
			//如果不存在 则创建一个新的消息集合
			if(msglist == null){
				msglist = new ConcurrentLinkedQueue<Message>();
				
				//写入Session
				request.getSession().setAttribute("msglist",msglist);
				
				//写入Application
				((Map<String, ConcurrentLinkedQueue<Message>>)request.getServletContext().getAttribute("totalmsglist")).put(username, msglist);
			}
			
			Iterator<Message> it = msglist.iterator();
			while(it.hasNext()){
				Message m = it.next();
				sb.append("<msg s=\""+m.getSender()+"\" m=\""+m.getMessage()+"\" t=\""+m.getSendtime().toString()+"\" r=\""+(m.getReader()==null?"":m.getReader())+"\" />");
				it.remove();
			}
			
			sb.append("</date>");
			out.print(sb.toString());		//输入获取到的消息字符串
			out.flush();
			out.close();
		}else {
			response.sendRedirect("login.html");
		}
	}
}
