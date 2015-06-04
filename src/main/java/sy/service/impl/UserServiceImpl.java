package sy.service.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sy.dao.BaseDaoI;
import sy.model.Tauth;
import sy.model.Trole;
import sy.model.Troletauth;
import sy.model.Tuser;
import sy.model.Tusertrole;
import sy.pageModel.DataGrid;
import sy.pageModel.User;
import sy.service.UserServiceI;
import sy.util.Encrypt;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserServiceI {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private BaseDaoI<Tusertrole> userroleDao;
	private BaseDaoI<Trole> roleDao;

	public BaseDaoI<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(BaseDaoI<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	public BaseDaoI<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(BaseDaoI<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	private BaseDaoI<Tuser> userDao;

	public BaseDaoI<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(BaseDaoI<Tuser> userDao) {
		this.userDao = userDao;
	}

	@Override
	public Serializable save(Tuser t) {
		return userDao.save(t);
	}

	@Override
	public User save(User user) {
		Tuser t = new Tuser();
		BeanUtils.copyProperties(user, t, new String[] { "pwd" });
		t.setId(UUID.randomUUID().toString());
		t.setPwd(Encrypt.e(user.getPwd()));
		t.setCreatedatetime(new Date());
		userDao.save(t);
		this.saveUserRole(user, t);
		BeanUtils.copyProperties(t, user);
		return user;
	}

	/**
	 * 保存用户和角色的关系
	 * 
	 * @param user
	 * @param t
	 */

	private void saveUserRole(User user, Tuser t) {
		userroleDao.executeHql("delete Tusertrole t where t.tuser=?", new Object[] { t });
		if (user.getRoleIds() != null) {
			for (String id : user.getRoleIds().split(",")) {
				Tusertrole tusertrole = new Tusertrole();
				tusertrole.setId(UUID.randomUUID().toString());
				tusertrole.setTrole(roleDao.get(Trole.class, id.trim()));
				tusertrole.setTuser(t);
				userroleDao.save(tusertrole);
			}
		}
	}

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", Encrypt.e(user.getPwd()));
		Tuser t = userDao.getObject("from Tuser t where t.name=:name and t.pwd=:pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user, new String[] { "pwd" });
			Map<String, String> authIdsMap = new HashMap<String, String>();
			String authIds = "";
			String authNames = "";
			Map<String, String> authUrlsMap = new HashMap<String, String>();
			String authUrls = "";
			String roleIds = "";
			String roleNames = "";
			boolean b1 = false;
			Set<Tusertrole> tusertroles = t.getTusertroles();
			if (tusertroles != null && tusertroles.size() > 0) {
				for (Tusertrole tusertrole : tusertroles) {
					Trole trole = tusertrole.getTrole();
					if (b1) {
						authIds = ",";
						authNames = ",";
					}
					roleIds += trole.getId();
					roleNames += trole.getName();
					b1 = true;

					Set<Troletauth> troletauths = trole.getTroletauths();
					if (troletauths != null && troletauths.size() > 0) {
						for (Troletauth troletauth : troletauths) {
							Tauth tauth = troletauth.getTauth();
							authIdsMap.put(tauth.getCid(), tauth.getName());
							authUrlsMap.put(tauth.getCid(), tauth.getUrl());
						}
					}
				}
			}
			boolean b2 = false;
			for (String id : authIdsMap.keySet()) {
				if (b2) {
					authIds += ",";
					authNames += ",";
				}
				authIds += id;
				authNames += authIdsMap.get(id);
				b2 = true;
			}
			user.setAuthIds(authIds);
			user.setAuthNames(authNames);
			user.setRoleIds(roleIds);
			user.setRoleNames(roleNames);
			boolean b3 = false;
			for (String id : authUrlsMap.keySet()) {
				if (b3) {
					authUrls += ",";
				}
				authUrls += authUrlsMap.get(id);
				b3 = true;
			}
			user.setAuthUrls(authUrls);
			return user;
		} else {
			return null;
		}
	}

	public DataGrid datagrid(User user) {
		DataGrid j = new DataGrid();
		j.setRows(this.changeModel(this.find(user)));
		j.setTotal(this.total(user));
		return j;
	}

	private List<User> changeModel(List<Tuser> tusers) {
		List<User> users = new ArrayList<User>();
		if (tusers != null && tusers.size() > 0) {
			for (Tuser tu : tusers) {
				User u = new User();
				BeanUtils.copyProperties(tu, u);

				Set<Tusertrole> tusertroles = tu.getTusertroles();
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				if (tusertroles != null && tusertroles.size() > 0) {
					for (Tusertrole tusertrole : tusertroles) {
						if (tusertrole.getTrole() != null) {
							if (b) {
								roleIds += ",";
								roleNames += ",";
							}
							roleIds += tusertrole.getTrole().getId();
							roleNames += tusertrole.getTrole().getName();
							b = true;
						}
					}
				}
				u.setRoleIds(roleIds);
				u.setRoleNames(roleNames);

				users.add(u);
			}
		}
		return users;
	}

	private List<Tuser> find(User user) {
		String hql = "from Tuser t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(user, hql, values);

		if (user.getSort() != null && user.getOrder() != null) {
			hql += " order by " + user.getSort() + " " + user.getOrder();
		}
		return userDao.find(hql, values, user.getPage(), user.getRows());
	}

	private Long total(User user) {
		String hql = "select count(*) from Tuser t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(user, hql, values);
		return userDao.count(hql, values);
	}

	private String addWhere(User user, String hql, List<Object> values) {
		if (user.getName() != null && !user.getName().trim().equals("")) {
			hql += " and t.name like ? ";
			values.add("%%" + user.getName().trim() + "%%");
		}
		return hql;
	}

	public void remove(String ids) {
		/*
		 * String[] nids = ids.split(","); String hql = "delete Tuser t where t.id in ("; for (int i = 0; i < nids.length; i++) { if (i > 0) { hql += ","; } hql += "'" + nids[i] + "'"; } hql += ")"; userDao.executeHql(hql);
		 */
		if (ids != null) {
			for (String id : ids.split(",")) {
				if (!id.trim().equals("0")) {
					Tuser u = userDao.get(Tuser.class, id.trim());
					if (u != null) {
						userroleDao.executeHql("delete Tusertrole t where t.tuser = ?", new Object[] { u });
						userDao.delete(u);
					}
				}
			}
		}
	}

	@Override
	public User edit(User user) {
		Tuser t = userDao.get(Tuser.class, user.getId());
		BeanUtils.copyProperties(user, t, new String[] { "id", "pwd" });
		if (user.getCreatedatetime() != null) {
			t.setCreatedatetime(new Date());
		}
		if (user.getModifydatetime() != null) {
			t.setModifydatetime(new Date());
		}
		if (user.getPwd() != null && !user.getPwd().trim().equals("")) {
			t.setPwd(Encrypt.e(user.getPwd()));
		}
		this.saveUserRole(user, t);
		return user;
	}

	@Override
	public void editUserInfo(User user) {
		if (user.getPwd() != null && !user.getPwd().trim().equals("")) {
        Tuser t=userDao.get(Tuser.class, user.getId());
        t.setPwd(Encrypt.e(user.getPwd()));
		}
	}
}
