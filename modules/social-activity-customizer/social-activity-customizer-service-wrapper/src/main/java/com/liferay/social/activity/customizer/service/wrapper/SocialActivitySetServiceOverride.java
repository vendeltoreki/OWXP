package com.liferay.social.activity.customizer.service.wrapper;

import com.liferay.social.kernel.service.SocialActivitySetLocalServiceWrapper;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Istvan Sajtos
 */
@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class SocialActivitySetServiceOverride extends SocialActivitySetLocalServiceWrapper {

	public SocialActivitySetServiceOverride() {
		super(null);
	}

}