package sy.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import sy.pageModel.SessionInfo;
import sy.util.RequestUtil;
import sy.util.ResourceUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
@SuppressWarnings("all")
public class AuthInterceptor extends MethodFilterInterceptor {
	private static final Logger logger = Logger
			.getLogger(AuthInterceptor.class);

	@Override
	public String doIntercept(ActionInvocation actionInvocation)
			throws Exception {
		ActionContext actionContext = actionInvocation.getInvocationContext();
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext
				.getRequest().getSession()
				.getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo.getLoginName().equals("admin")) {
			return actionInvocation.invoke();
		}
		String requestPath = RequestUtil.getRequestPath(ServletActionContext
				.getRequest());
		String authUrls = sessionInfo.getAuthUrls();
		boolean b = false;
		for (String url : authUrls.split(",")) {
			if (requestPath.equals(url)) {
				b = true;
				break;
			}
		}
		if (b) {
			return actionInvocation.invoke();
		}
		ServletActionContext.getRequest().setAttribute("msg", "您没有访问此功能的权限！权限路径为[" + requestPath + "]请联系管理员给你赋予相应权限。");
		return "noAuth";
	}
}
