package sy.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import sy.action.base.BaseAction;
import sy.service.RepairServiceI;

@Action("repairAction")
public class RepairAction extends BaseAction {
	private RepairServiceI repairService;

	public RepairServiceI getRepairService() {
		return repairService;
	}

	@Autowired
	public void setRepairService(RepairServiceI repairService) {
		this.repairService = repairService;
	}

	public void init(){
       repairService.repair();		
	}

}
