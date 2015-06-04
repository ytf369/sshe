package sy.service;

import java.util.List;

import sy.pageModel.DataGrid;
import sy.pageModel.Role;

public interface RoleServiceI {
public DataGrid datagrid(Role role);

public List<Role> combobox();

public void add(Role role);

public void edit(Role role);
public void delete(String ids);
}
