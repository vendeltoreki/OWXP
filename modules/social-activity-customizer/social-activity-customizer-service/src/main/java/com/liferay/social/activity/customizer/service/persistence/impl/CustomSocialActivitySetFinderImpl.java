package com.liferay.social.activity.customizer.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.social.activity.customizer.CustomFinderHelperUtil;
import com.liferay.social.activity.customizer.service.persistence.CustomSocialActivitySetFinder;
import com.liferay.social.kernel.model.SocialActivitySet;

import java.util.List;

/**
 * @author Istvan Sajtos
 */
public class CustomSocialActivitySetFinderImpl
	extends CustomSocialActivitySetFinderBaseImpl
	implements CustomSocialActivitySetFinder {

	public static final String FIND_BY_USERID_CLASSNAMEID =
		CustomSocialActivitySetFinder.class.getName() + ".findByU_C";

	public List<SocialActivitySet> findByU_C(
		long userId, long classNameId, int begin, int end) {

		Session session = null;

		try {
			session = CustomFinderHelperUtil.openPortalSession();

			String sql = CustomSQLUtil.get(
				CustomSocialActivitySetFinderImpl.class,
				FIND_BY_USERID_CLASSNAMEID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.setCacheable(false);
			q.addEntity(
				"SocialActivitySet", CustomFinderHelperUtil.getImplClass(
					"com.liferay.portlet.social.model.impl." +
						"SocialActivitySetImpl",
					PortalClassLoaderUtil.getClassLoader()));

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);
			qPos.add(classNameId);

			return (List<SocialActivitySet>)QueryUtil.list(
				q, getDialect(), begin, end);
		}
		catch (Exception e) {
			try {
				throw new SystemException(e);
			}
			catch (SystemException se) {
				se.printStackTrace();
			}
		}
		finally {
			closeSession(session);
		}

		return null;
	}

}