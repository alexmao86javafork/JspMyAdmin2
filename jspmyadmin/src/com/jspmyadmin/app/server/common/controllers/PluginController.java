/**
 * 
 */
package com.jspmyadmin.app.server.common.controllers;

import com.jspmyadmin.app.server.common.beans.CommonListBean;
import com.jspmyadmin.app.server.common.logic.PluginLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
@WebController(authentication = true, path = "/server_plugins")
public class PluginController extends Controller<CommonListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(CommonListBean bean, View view) throws Exception {
		PluginLogic pluginLogic = null;
		try {
			super.clearForServer();
			pluginLogic = new PluginLogic();
			pluginLogic.fillBean(bean);
		} finally {
			pluginLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_COMMON_PLUGINLIST);
	}

	@Override
	protected void handlePost(CommonListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
