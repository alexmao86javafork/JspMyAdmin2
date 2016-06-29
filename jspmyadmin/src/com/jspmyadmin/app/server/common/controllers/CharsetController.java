/**
 * 
 */
package com.jspmyadmin.app.server.common.controllers;

import com.jspmyadmin.app.server.common.beans.CommonListBean;
import com.jspmyadmin.app.server.common.logic.CharsetLogic;
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
@WebController(authentication = true, path = "/server_charsets")
public class CharsetController extends Controller<CommonListBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(CommonListBean bean, View view) throws Exception {
		CharsetLogic charsetLogic = null;
		try {
			super.clearForServer();
			charsetLogic = new CharsetLogic();
			charsetLogic.fillBean(bean);
		} finally {
			charsetLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_SERVER_COMMON_CHARSETLIST);
	}

	@Override
	protected void handlePost(CommonListBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
