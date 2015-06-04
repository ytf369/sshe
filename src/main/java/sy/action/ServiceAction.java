package sy.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import sy.action.base.BaseAction;
import sy.model.Sservice;
import sy.service.impl.ServiceServiceImpl;
@Namespace("/")
@Action(value = "serviceAction", results = { @Result(name = "serviceAdd", location = "/admin/serviceAdd.jsp"), @Result(name = "serviceEdit", location = "/admin/serviceEdit.jsp") })
public class ServiceAction extends BaseAction implements ModelDriven<Sservice> {
private Sservice sservice=new Sservice();
@Autowired
private ServiceServiceImpl serviceservice;

public ServiceServiceImpl getServiceservice() {
	return serviceservice;
}
public void setServiceservice(ServiceServiceImpl serviceservice) {
	this.serviceservice = serviceservice;
}

public void treegrid() {
	writeJson(serviceservice.createservicetree(sservice));
}



	@Override
	public Sservice getModel() {
		return sservice;
	}

}
