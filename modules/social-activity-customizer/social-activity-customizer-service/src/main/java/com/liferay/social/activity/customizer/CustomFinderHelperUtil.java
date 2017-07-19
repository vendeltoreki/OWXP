package com.liferay.social.activity.customizer;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Istvan Sajtos
 */
public class CustomFinderHelperUtil {

	public static Class getImplClass(
		String implClassName, ClassLoader classLoader) {

		Class<?> clazz = _classMap.get(implClassName);

		if (clazz == null) {
			try {
				if (classLoader == null) {
					Thread currentThread = Thread.currentThread();

					classLoader = currentThread.getContextClassLoader();
				}

				clazz = classLoader.loadClass(implClassName);

				_classMap.put(implClassName, clazz);
			}
			catch (Exception e) {
				_log.error("Unable find model " + implClassName, e);
			}
		}

		return clazz;
	}

	public static Session openPortalSession() throws ORMException {
		return _sessionFactory.openSession();
	}

	private static Log _log = LogFactoryUtil.getLog(
		CustomFinderHelperUtil.class);

	private static Map<String, Class> _classMap = new HashMap<>();
	private static SessionFactory _sessionFactory =
		(SessionFactory)PortalBeanLocatorUtil.locate("liferaySessionFactory");

}