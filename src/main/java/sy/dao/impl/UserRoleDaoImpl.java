package sy.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import sy.dao.UserRoleDaoI;
import sy.model.Tusertrole;
@Repository("userroleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<Tusertrole> implements UserRoleDaoI {

}
