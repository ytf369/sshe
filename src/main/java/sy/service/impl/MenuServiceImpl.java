package sy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sy.dao.BaseDaoI;
import sy.model.Tmenu;
import sy.pageModel.Menu;
import sy.service.MenuServiceI;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuServiceI {
	@Autowired
	private BaseDaoI<Tmenu> menuDao;

	public BaseDaoI<Tmenu> getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(BaseDaoI<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public List<Menu> getTree(String id) {
		List<Menu> nl = new ArrayList<Menu>();
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null) {
			hql = "from Tmenu t where t.tmenu is null";
		} else {
			hql = "from Tmenu t where t.tmenu.id=:id";
			params.put("id", id);
		}
		List<Tmenu> l = menuDao.find(hql, params);
		if (l != null && l.size() > 0) {
			for (Tmenu t : l) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Set<Tmenu> set = t.getTmenus();
				if (set != null && !set.isEmpty()) {
					m.setState("closed");
				} else {
					m.setState("open");
				}
				nl.add(m);
			}
		}
		return nl;
	}

	@Override
	public List<Menu> getAllTreeNode() {
		List<Menu> nl = new ArrayList<Menu>();
		String hql = "from Tmenu t";
		List<Tmenu> l = menuDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Tmenu t : l) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes=new HashMap<String, Object>();
				attributes.put("url", t.getUrl());
				m.setAttributes(attributes);
				Tmenu tm = t.getTmenu();
				if (tm != null) {
					m.setPid(t.getTmenu().getId());
				}
				nl.add(m);
			}
		}
		return nl;
	}
}
