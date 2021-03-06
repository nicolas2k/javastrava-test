package test.api.auth.impl.retrofit;

import static org.junit.Assert.fail;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.AthleteServiceImpl;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class TokenServiceImplTest {
	/**
	 * <p>
	 * Test behaviour when a token is deauthorised TWICE
	 * </p>
	 *
	 * <p>
	 * Should fail the second time
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeauthorise_deauthorisedToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// 1. Get a deauthorised token
				final Token token = TestUtils.getValidToken();

				// 2. Attempt to deauthorise it twice
				final Strava service = new Strava(token);
				service.deauthorise(token);
				try {
					service.deauthorise(token);
				} catch (final UnauthorizedException e) {
					// This is the expected behaviour
				}
			});
	}

	/**
	 * <p>
	 * Test deauthorisation of an invalid (i.e. non-existent) token
	 * </p>
	 *
	 * <p>
	 * Should fail
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeauthorise_invalidToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// 1. Get a valid token
				Token token = TestUtils.getValidToken();

				// 2. Get a service implementation for the valid token
				final Strava service = new Strava(token);

				// 3. Get an INVALID token
				token = TestUtils.INVALID_TOKEN;

				// 4. Attempt to deauthorise the invalid token
				try {
					service.deauthorise(token);
				} catch (final UnauthorizedException e) {
					// This is the expected behaviour
				}
			});
	}

	/**
	 * <p>
	 * Test deauthorisation of a valid token
	 * </p>
	 *
	 * <p>
	 * Should succeed; token should no longer be able to be used for access
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeauthorise_validToken() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// 2. Attempt to use the token to get a service implementation
				try {
					AthleteServiceImpl.instance(TestUtils.getRevokedToken()).getAuthenticatedAthlete();
				} catch (final UnauthorizedException e) {
					// This is expected behaviour
					return;
				}

				// 3. We should NOT get a service implementation
				fail("Got a usable service implementation despite successfully deauthorising the token");
			});
	}
}
