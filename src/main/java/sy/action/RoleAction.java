package sy.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ModelDriven;
import sy.action.base.BaseAction;
import sy.pageModel.Json;
import sy.pageModel.Role;
import sy.service.RoleServiceI;

@Namespace("/")
@Action(value = "roleAction", results = { @Result(name = "roleAdd", location = "/admin/roleAdd.jsp"), @Result(name = "roleEdit", location = "/admin/roleEdit.jsp") })
public class RoleAction extends BaseAction implements ModelDriven<Role> {
	private Role role = new Role();

	@Override
	public Role getModel() {
		return role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Autowired
	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}

	/**
	 * 获取角色下拉列表
	 */
	public void doNotNeedSession_combobox() {
		writeJson(roleService.combobox());
	}

	public void datagrid() {
		writeJson(roleService.datagrid(role));
	}

	public String roleAdd() {
		return "roleAdd";
	}

	/*
	 * 添加一个角色
	 */
	public void add() {
		Json j = new Json();
		try {
			roleService.add(role);
			j.setSuccess(true);
			j.setMsg("添加角色成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("添加角色失败");
		}
		writeJson(j);
	}

	public String roleEdit() {
		return "roleEdit";
	}

	public void edit() {
		Json j = new Json();
		try {
			roleService.edit(role);
			j.setSuccess(true);
			j.setMsg("编辑角色成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("编辑角色失败");
		}
		writeJson(j);
	}

	public void delete() {
		Json j = new Json();
		roleService.delete(role.getIds());
		j.setSuccess(true);
		j.setMsg("删除成功！");
		super.writeJson(j);
	}
}
