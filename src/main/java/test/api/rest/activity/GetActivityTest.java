package test.api.rest.activity;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.model.StravaActivityTest;
import test.api.rest.APIGetTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetActivityTest extends APIGetTest<StravaActivity, Integer> {
	/**
	 *
	 */
	public GetActivityTest() {
		this.getCallback = (api, id) -> api.getActivity(id, null);
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * <p>
	 * Test retrieval of a known {@link StravaActivity}, complete with all
	 * {@link StravaSegmentEffort efforts}
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testGetActivity_run() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = this.getCallback.run(api(), TestUtils.ACTIVITY_RUN_WITH_SEGMENTS);
			assertNotNull(activity);
			validate(activity);
		} );
	}

	@Test
	public void testGetActivity_runOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaActivity activity = this.getCallback.run(api(), TestUtils.ACTIVITY_RUN_OTHER_USER);
			assertNotNull(activity);
			StravaActivityTest.validateActivity(activity);
		} );
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaActivity activity) throws Exception {
		StravaActivityTest.validateActivity(activity);
	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}
}
