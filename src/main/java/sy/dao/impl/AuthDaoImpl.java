package sy.dao.impl;

import org.springframework.stereotype.Repository;

import sy.dao.AuthDaoI;
import sy.model.Tauth;
@Repository("authDao")
public class AuthDaoImpl extends BaseDaoImpl<Tauth> implements AuthDaoI{

}
