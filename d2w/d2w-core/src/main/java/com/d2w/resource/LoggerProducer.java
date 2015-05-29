package com.d2w.resource;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produce an injectable logger.
 * 
 */
public class LoggerProducer {

	/**
	 * Produce an injectable logger.
	 * 
	 * @param injectionPoint
	 * @return an injectable slf4j logger.
	 */
	@Produces
	public Logger produce(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember()
				.getDeclaringClass());
	}
}
