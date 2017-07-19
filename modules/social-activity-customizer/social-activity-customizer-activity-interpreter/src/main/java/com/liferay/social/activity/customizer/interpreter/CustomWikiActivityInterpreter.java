package com.liferay.social.activity.customizer.interpreter;

import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Istvan Sajtos
 */
@Component(
		immediate = true,
		property = {"javax.portlet.name=" + WikiPortletKeys.WIKI, "service.ranking:Integer=100"},
		service = SocialActivityInterpreter.class
)
public class CustomWikiActivityInterpreter extends BaseSocialActivityInterpreter {

	// TODO enter required service methods

}