package com.liferay.social.activity.customizer.service.wrapper;

import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.social.activity.customizer.service.persistence.CustomSocialActivitySetFinder;
import com.liferay.social.kernel.model.SocialActivitySet;
import com.liferay.social.kernel.service.SocialActivitySetLocalServiceWrapper;
import com.liferay.wiki.model.WikiPage;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Istvan Sajtos
 */
@Component(immediate = true, property = {}, service = ServiceWrapper.class)
public class SocialActivitySetServiceOverride
	extends SocialActivitySetLocalServiceWrapper {

	public SocialActivitySetServiceOverride() {
		super(null);
	}

	@Override
	public List<SocialActivitySet> getUserActivitySets(
		long userId, int start, int end) {

		long classNameId = _classNameLocalService.getClassNameId(
			WikiPage.class);

		return _customSocialActivitySetFinder.findByU_C(
			userId, classNameId, start, end);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CustomSocialActivitySetFinder _customSocialActivitySetFinder;

}