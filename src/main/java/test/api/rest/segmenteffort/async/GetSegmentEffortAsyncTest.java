/**
 *
 */
package test.api.rest.segmenteffort.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.segmenteffort.GetSegmentEffortTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author danshannon
 *
 */
public class GetSegmentEffortAsyncTest extends GetSegmentEffortTest {
	/**
	 *
	 */
	public GetSegmentEffortAsyncTest() {
		this.getCallback = (api, id) -> api.getSegmentEffortAsync(id).get();
	}

	/**
	 * Check that an effort on a private activity is not returned
	 *
	 * @throws Exception
	 */
	@Override
	public void testGetSegmentEffort_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentEffortAsync(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).get();
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
	@Override
	public void testGetSegmentEffort_privateActivityViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentEffort effort = apiWithViewPrivate().getSegmentEffortAsync(TestUtils.SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID).get();
			assertNotNull(effort);
			assertEquals(StravaResourceState.DETAILED, effort.getResourceState());
		});
	}

}
