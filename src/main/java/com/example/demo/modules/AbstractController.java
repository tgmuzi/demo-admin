package com.example.demo.modules;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.modules.user.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	// protected static String GET_PAGE_PATH(String requestMapping, String pageType)
	// {
	// if (StringUtils.isBlank(requestMapping)) {
	// throw new ServiceException("requestMapping参数不能为空!");
	// }
	// String temp = null;
	// StringBuffer sb = new StringBuffer();
	// if (StringUtils.startsWith(requestMapping, "/")) {
	// temp = StringUtils.substringAfter(requestMapping, "/");
	// } else {
	// temp = requestMapping;
	// }
	// sb.append(temp).append("/").append(StringUtils.substringAfterLast(requestMapping,
	// "/"));
	// return sb.toString() + Character.toUpperCase(pageType.charAt(0)) +
	// pageType.substring(1);
	// }

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected String getUserCode() {
		return getUser().getUserName();
	}

	protected String getContextPath() {
		return request.getContextPath();
	}

	protected HttpServletRequest getHttpServletRequest() {
		return request;
	}
}
