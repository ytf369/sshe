package sy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sy.comparator.AuthComparator;
import sy.dao.BaseDaoI;
import sy.model.Tauth;
import sy.model.Troletauth;
import sy.pageModel.Auth;
import sy.pageModel.TreeNode;
import sy.service.AuthServiceI;

@Service("authService")
public class AuthServiceImpl implements AuthServiceI {
	private BaseDaoI<Tauth> authDao;
	private BaseDaoI<Troletauth> roleauthDao;

	public BaseDaoI<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(BaseDaoI<Tauth> authDao) {
		this.authDao = authDao;
	}

	public BaseDaoI<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(BaseDaoI<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public List<TreeNode> tree(Auth auth, boolean b) {
		List<Object> param = new ArrayList<Object>();
		String hql = "from Tauth t where t.tauth.cid is null order by t.seq";
		if (auth != null && auth.getId() != null && !auth.getId().trim().equals("")) {
			hql = "from Tauth t where t.tauth.cid = ? order by t.seq";
			param.add(auth.getId());
		}
		List<Tauth> l = authDao.find(hql, param);
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (Tauth t : l) {
			tree.add(this.tree(t, b));
		}
		return tree;
	}

	private TreeNode tree(Tauth t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getCid());
		node.setText(t.getName());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (t.getTauths() != null && t.getTauths().size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<Tauth> l = new ArrayList<Tauth>(t.getTauths());
				Collections.sort(l, new AuthComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Tauth r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
				node.setState("open");
			}
		}
		return node;
	}

	@Override
	public List<Auth> treegrid(Auth auth) {
		List<Tauth> l;
		if (auth != null && auth.getId() != null) {
			l = authDao.find("from Tauth t where t.tauth.cid=? order by t.seq", new Object[] { auth.getId() });
		} else {
			l = authDao.find("from Tauth t where t.tauth is null order by t.seq");
		}
		return changeModel(l);
	}

	private List<Auth> changeModel(List<Tauth> tauths) {
		List<Auth> l = new ArrayList<Auth>();
		if (tauths != null && tauths.size() > 0) {
			for (Tauth t : tauths) {
				Auth a = new Auth();
				BeanUtils.copyProperties(t, a);
				if (t.getTauth() != null) {
					a.setPid(t.getTauth().getCid());
					a.setPname(t.getTauth().getName());
				}
				if (countChildren(t.getCid()) > 0) {
					a.setState("closed");
				}
				l.add(a);
			}
		}

		return l;
	}

	/**
	 * 统计有多少子节点
	 */
	private Long countChildren(String pid) {

		return authDao.count("select count(*) from Tauth t where t.tauth.cid = ?", new Object[] { pid });
	}

	/**
	 * 添加权限功能
	 */
	public void add(Auth auth) {
		Tauth t = new Tauth();
		BeanUtils.copyProperties(auth, t);
		t.setCid(UUID.randomUUID().toString());
		if (auth.getPid() != null && auth.getPid().equals(auth.getCid())) {
			t.setTauth(authDao.get(Tauth.class, auth.getPid()));
		}
		authDao.save(t);
	}
}
