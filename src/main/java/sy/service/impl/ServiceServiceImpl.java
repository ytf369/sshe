package sy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.dao.impl.ServiceDao;
import sy.dao.impl.SmenuDaoImpl;
import sy.model.Smenu;
import sy.model.Sservice;
import sy.pageModel.Pmenu;

@Service("serviceservice")
public class ServiceServiceImpl {
@Autowired
private ServiceDao servicedao;
@Autowired
private SmenuDaoImpl smenudao;
public ServiceDao getServicedao() {
	return servicedao;
}

public void setServicedao(ServiceDao servicedao) {
	this.servicedao = servicedao;
}

public SmenuDaoImpl getSmenudao() {
	return smenudao;
}

public void setSmenudao(SmenuDaoImpl smenudao) {
	this.smenudao = smenudao;
}

//生成服务及菜单树
public List<Sservice> createservicetree(Sservice sservice) {
	String hql="from Sservice";
	List<Sservice> services=servicedao.find(hql);
	List<sy.pageModel.Service> pservices=new ArrayList<sy.pageModel.Service>();
	//获取所有服务列表
	for(int i=0;i<=services.size();i++){
		sy.pageModel.Service pservice=new sy.pageModel.Service();
		//服务子菜单
	//	select * from 
		Set<Smenu>  menus=services.get(i).getSmens();
		List<Integer> ids=new ArrayList<Integer>();
		for(Smenu menu: menus){
			ids.add(menu.getId());
		}
		System.out.println("所有子菜单"+menus.toString());
	 //获取所有父级菜单
	List<Smenu> fmenus=	smenudao.find(" from Smenu m where m.smenu is null");
	System.out.println("所有父级菜单："+fmenus);
	ArrayList<Pmenu> pmenulist=new ArrayList<Pmenu>();
		for(int j=0;j<=fmenus.size();j++){ 
			Pmenu pmenu=new Pmenu();
		
		    	Query query=smenudao.getSessionFactory().getCurrentSession().createQuery("from Smenu m where m.smenu=:menu and id in( :ids)");
				query.setParameter("menu", fmenus.get(j));
				query.setParameterList("ids", ids);
				List<Smenu> cmenu=query.list();
				System.out.println(j+"级父级菜单子菜单列表："+cmenu.toString());
				pmenu.setSmenus(cmenu);
				pmenulist.add(pmenu);
			}
		pservice.setChildren(pmenulist);
		pservices.add( pservice);
		}
	

	return null;
}

}
