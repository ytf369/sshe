package sy.service;

import java.util.List;

import sy.pageModel.Menu;

public interface MenuServiceI {
public List<Menu> getTree(String id);
public List<Menu> getAllTreeNode();
}
