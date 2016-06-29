/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/15
 *
 */
@WebController(authentication = true, path = "/database_structure")
public class TableListController extends Controller<StructureBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(StructureBean bean, View view) throws Exception {

		StructureLogic structureLogic = null;
		try {
			super.fillBasics(bean);
			super.clearForDb();
			super.checkForDb(bean);
			structureLogic = new StructureLogic();
			structureLogic.fillBean(bean, true);
			super.generateToken(bean);
		} finally {
			structureLogic = null;
		}
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_TABLES);
	}

	@Override
	protected void handlePost(StructureBean bean, View view) throws Exception {
		this.handleGet(bean, view);
	}

}
