/**
 * 
 */
package com.jspmyadmin.framework.tag.messages;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.jspmyadmin.framework.tag.support.AbstractSimpleTagSupport;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.MessageReader;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/01/27
 *
 */
public class PrintTag extends AbstractSimpleTagSupport {

	private String key = null;
	private String scope = null;

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public void doTag() throws JspException, IOException {

		PageContext pageContext = (PageContext) super.getJspContext();
		MessageReader messageReader = (MessageReader) pageContext
				.getAttribute(FrameworkConstants.PAGE_CONTEXT_MESSAGES);
		Object temp = null;
		if (scope == null) {
			key = messageReader.getMessage(key);
			JspWriter jspWriter = pageContext.getOut();
			jspWriter.write(key);
			return;
		} else if (FrameworkConstants.COMMAND.equals(scope)) {
			temp = pageContext.getRequest().getAttribute(
					FrameworkConstants.COMMAND);
			temp = super.getReflectValue(temp, key);
		} else if (FrameworkConstants.PAGE.equals(scope)) {
			temp = pageContext.getAttribute(key);
		} else if (FrameworkConstants.REQUEST.equals(scope)) {
			temp = pageContext.getRequest().getAttribute(key);
		}
		if (temp != null) {
			key = messageReader.getMessage(temp.toString());
			if (key != null) {
				JspWriter jspWriter = pageContext.getOut();
				jspWriter.write(key);
			}
		}
	}
}
