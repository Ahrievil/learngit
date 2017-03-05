package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import po.OaModule;
import service.ModService;
import util.BaseServlet;
import util.PageUtil;

public class ModServlet extends BaseServlet{

	private ModService service = new ModService();
	
	public String queryAll(HttpServletRequest request,HttpServletResponse response){
		String curPage = request.getParameter("curPage");
		System.out.println("curPage======>" + curPage);
		if (curPage == null || curPage == "") {
			curPage = "1";
		}
		PageUtil util = service.queryByPage(Integer.parseInt(curPage));
		request.setAttribute("util", util);
		request.setAttribute("curPage", curPage);
		Long l = System.currentTimeMillis();
		request.getSession().setAttribute("check", l+"");
		return "/files/listmokuai.jsp";
	}
	public String insertMod(HttpServletRequest request,HttpServletResponse response){
		String moName = request.getParameter("moname");
		String moMark = request.getParameter("mobiaozhi");
		String reLevel = request.getParameter("modmission");
		String prname = request.getParameter("prono");
		String rname = request.getParameter("xuqiu");
		String moOther = request.getParameter("moddescription");
		String reOther = request.getParameter("modneedDescription");
		OaModule mod = new OaModule();
		mod.setMoName(moName);
		mod.setPrname(prname);
		mod.setReLevel(reLevel);
		mod.setMoOther(moOther);
		mod.setMoMark(moMark);
		mod.setRname(rname);
		mod.setReOther(reOther);
		int k = service.insert(mod);
		request.setAttribute("result", "�ɹ�����"+k+"����¼��");
		return queryAll(request, response);
	}
	public String deleteSecMod(HttpServletRequest request,HttpServletResponse response){
		String [] array=request.getParameterValues("hello");
		String [] array1=request.getParameterValues("world");
		int k = 0;
		for(int i=0;i<array.length;i++){
			System.out.println("id-"+array[i]);
			System.out.println("array1[i]-"+array1.length);
			if(array1[i].equals("yes")){
				System.out.println("id-"+array[i]);
				k+=service.deleteModById(Integer.parseInt(array[i]));
			}
		}
		request.setAttribute("result", "�ɹ�ɾ��"+k+"����¼��");
		return queryAll(request, response);
	}
	public String queryForUpdate(HttpServletRequest request,HttpServletResponse response){
		String moid = request.getParameter("moid");
		OaModule mod = service.load(Integer.parseInt(moid));
		request.setAttribute("modu", mod);
		return "/files/editmokuai.jsp";
	}
	public String update(HttpServletRequest request,HttpServletResponse response){
		String moid = request.getParameter("moid");
		String moName = request.getParameter("moname");
		String moMark = request.getParameter("mobiaozhi");
		String reLevel = request.getParameter("modmission");
		String moOther = request.getParameter("moddescription");
		String reOther = request.getParameter("modneedDescription");
		String prname = request.getParameter("prono");
		String rname = request.getParameter("nno");
		OaModule mod = new OaModule();
		mod.setMoid(Integer.parseInt(moid));
		mod.setMoName(moName);
		mod.setMoOther(moOther);
		mod.setReOther(reOther);
		mod.setMoMark(moMark);
		mod.setReLevel(reLevel);
		mod.setPrname(prname);
		mod.setRname(rname);
		System.out.println(mod);
		int k = service.update(mod);
		request.setAttribute("result", "�ɹ�����"+k+"����¼��");
		return queryAll(request, response);
	}
	public String getInFo(HttpServletRequest request,HttpServletResponse response){
		String moid = request.getParameter("moid");
		OaModule mod = service.load(Integer.parseInt(moid));
		request.setAttribute("mk", mod);
		String tag = request.getParameter("tag");
		if("chakan".equals(tag)){
			return "/files/listmokuaimingxi.jsp";
		}else if("biangeng".equals(tag)){
			return "/files/mokuaixuqiu.jsp";
		}else{
			return "";
		}
	}
	public String changeMoOther(HttpServletRequest request,HttpServletResponse response){
		String moid = request.getParameter("moid");
		String moOther = request.getParameter("modneedDescription");
		OaModule mod = new OaModule();
		mod.setMoid(Integer.parseInt(moid));
		mod.setMoOther(moOther);
		int k = service.updateOther(mod);
		String result = "������ʧ�ܣ�";
		if(k == 1){
			result = "�������ɹ���";
		}
		request.setAttribute("result", result);
		return queryAll(request, response);
	}
	public void loadAllName(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String rname = request.getParameter("rname");
		System.out.println(rname);
		List<String> list = service.loadAllName(rname);
		JSONArray array1 = JSONArray.fromObject(list);
		System.out.println(array1.toString());
		out.println(array1.toString());
		out.flush();
		out.close();
	}
	
}