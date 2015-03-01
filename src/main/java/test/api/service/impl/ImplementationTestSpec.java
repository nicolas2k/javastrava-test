package test.api.service.impl;


/**
 * <p>
 * Test classes specified to test implementation of services should implement this set of tests.
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface ImplementationTestSpec {
	public void testImplementation_validToken();

	public void testImplementation_invalidToken();

	public void testImplementation_revokedToken();

	public void testImplementation_implementationIsCached();

	public void testImplementation_differentImplementationIsNotCached();

}
