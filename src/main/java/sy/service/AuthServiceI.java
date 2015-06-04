package sy.service;

import java.util.List;

import sy.pageModel.Auth;
import sy.pageModel.TreeNode;

public interface AuthServiceI {

	/**
	 * 获取权限树
	 * 
	 * @param auth
	 * @param b
	 *            是否递归子节点
	 * @return
	 */
	public List<TreeNode> tree(Auth auth, boolean b);

	public List<Auth> treegrid(Auth auth);

	public void add(Auth auth);

}
