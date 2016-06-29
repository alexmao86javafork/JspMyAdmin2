/**
 * 
 */
package com.jspmyadmin.app.server.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.server.common.beans.CommonListBean;
import com.jspmyadmin.framework.db.AbstractLogic;
import com.jspmyadmin.framework.db.ApiConnection;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/10
 *
 */
public class CharsetLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 * @throws Exception
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException, JSONException, Exception {

		CommonListBean charsetBean = null;
		List<String[]> charsetInfoList = null;
		String[] charsetInfo = null;
		int length = 0;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		EncDecLogic encDecLogic = null;
		JSONObject jsonObject = null;
		String orderBy = "CHARACTER_SET_NAME";
		String sort = " ASC";
		boolean type = false;
		StringBuilder query = null;
		try {
			charsetBean = (CommonListBean) bean;
			apiConnection = getConnection(false);
			encDecLogic = new EncDecLogic();
			if (!super.isEmpty(charsetBean.getToken())) {
				jsonObject = new JSONObject(encDecLogic.decode(charsetBean.getToken()));
				if (jsonObject.has(FrameworkConstants.NAME)) {
					orderBy = jsonObject.getString(FrameworkConstants.NAME);
				}
				if (jsonObject.has(FrameworkConstants.TYPE)) {
					type = jsonObject.getBoolean(FrameworkConstants.TYPE);
				}
			}
			if (type) {
				sort = " DESC";
			}
			query = new StringBuilder();
			query.append("SELECT a.CHARACTER_SET_NAME,a.COLLATION_NAME,");
			query.append("a.ID,a.IS_DEFAULT,a.IS_COMPILED,a.SORTLEN,b.DESCRIPTION,");
			query.append("b.MAXLEN FROM (SELECT * FROM information_schema.COLLATIONS) ");
			query.append("AS a LEFT JOIN (SELECT * FROM information_schema.CHARACTER_SETS) ");
			query.append("AS b ON a.CHARACTER_SET_NAME = b.CHARACTER_SET_NAME ORDER BY ");
			query.append(orderBy);
			query.append(sort);
			statement = apiConnection.preparedStatementSelect(query.toString());
			resultSet = statement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			length = resultSetMetaData.getColumnCount();

			charsetInfo = new String[length];
			for (int i = 0; i < length; i++) {
				charsetInfo[i] = resultSetMetaData.getColumnName(i + 1);
			}
			charsetBean.setColumnInfo(charsetInfo);

			charsetInfo = new String[length];
			for (int i = 0; i < length; i++) {
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.NAME, resultSetMetaData.getColumnName(i + 1));
				if (orderBy.equalsIgnoreCase(resultSetMetaData.getColumnName(i + 1))) {
					jsonObject.put(FrameworkConstants.TYPE, !type);
				} else {
					jsonObject.put(FrameworkConstants.TYPE, false);
				}
				charsetInfo[i] = encDecLogic.encode(jsonObject.toString());
			}
			charsetBean.setSortInfo(charsetInfo);

			charsetInfoList = new ArrayList<String[]>();
			while (resultSet.next()) {
				charsetInfo = new String[length];
				for (int i = 0; i < length; i++) {
					charsetInfo[i] = resultSet.getString(i + 1);
				}
				charsetInfoList.add(charsetInfo);
			}
			charsetBean.setType(Boolean.toString(type));
			charsetBean.setField(orderBy);
			charsetBean.setData_list(charsetInfoList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
