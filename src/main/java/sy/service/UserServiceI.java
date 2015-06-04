package sy.service;

import java.io.Serializable;

import sy.model.Tuser;
import sy.pageModel.DataGrid;
import sy.pageModel.User;

public interface UserServiceI {
public Serializable save(Tuser t);
public User save(User user);
public User login(User user);
public DataGrid datagrid(User user);
public void remove(String ids);
public User edit(User user);
public void editUserInfo(User user);
}
