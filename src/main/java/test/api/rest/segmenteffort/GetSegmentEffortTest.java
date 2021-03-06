package test.api.rest.segmenteffort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.rest.APIGetTest;
import test.issues.strava.Issue78;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class GetSegmentEffortTest extends APIGetTest<StravaSegmentEffort, Long> {
	/**
	 *
	 */
	public GetSegmentEffortTest() {
		this.getCallback = (api, id) -> api.getSegmentEffort(id);
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return TestUtils.SEGMENT_EFFORT_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return TestUtils.SEGMENT_EFFORT_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return TestUtils.SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned segment effort for a private activity, without view_private");
		});
	}

	/**
	 * Check that an effort on a private activity is returned with view_private scope
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetSegmentEffort_privateActivityViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = apiWithViewPrivate().getSegmentEffort(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID);
			assertNotNull(effort);
			assertEquals(StravaResourceState.DETAILED, effort.getResourceState());
		});
	}

	/**
	 * Check that an effort on a private segment is not returned
	 *
	 * @throws Exception
	 */
	@Override
	public void get_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#78
			if (new Issue78().isIssue()) {
				return;
			}
			// End of workaround
			super.get_private();

		});
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaSegmentEffort result) throws Exception {
		StravaSegmentEffortTest.validateSegmentEffort(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Long validId() {
		return TestUtils.SEGMENT_EFFORT_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return null;
	}

}
