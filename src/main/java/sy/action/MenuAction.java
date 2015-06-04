package sy.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import sy.action.base.BaseAction;
import sy.pageModel.Menu;
import sy.service.MenuServiceI;
@Namespace("/")
@Action("menuAction")
public class MenuAction extends BaseAction implements ModelDriven<Menu> {
	private Menu menu = new Menu();
	private MenuServiceI menuService;

	public MenuServiceI getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}
   //异步获取树节点
	public void getTreeNode() {
		writeJson(menuService.getTree(menu.getId()));
	}

	public void getAllTreeNode() {
		writeJson(menuService.getAllTreeNode());
	}
	public Menu getModel() {
		return menu;
	}
}
