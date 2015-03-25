package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaActivityZone;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaActivityZoneTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListActivityZonesTest extends StravaTest {
	/**
	 * <p>
	 * List {@link StravaActivityZone activity zones} for an {@link StravaActivity} which has them
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_hasZones() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivityZone> zones = strava().listActivityZones(TestUtils.ACTIVITY_WITH_ZONES);

				assertNotNull("Returned null activity zones for an activity with zones", zones);
				assertNotEquals("Returned an empty array of activity zones for an activity with zones", 0, zones.size());
				for (final StravaActivityZone zone : zones) {
					StravaActivityZoneTest.validateActivityZone(zone, zone.getResourceState());
				}
			}
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaActivityZone zones} for an {@link StravaActivity} which doesn't have any
	 * </p>
	 *
	 * <p>
	 * Should return an empty array
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_hasNoZones() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivityZone> zones = strava().listActivityZones(TestUtils.ACTIVITY_WITHOUT_ZONES);

				assertNotNull("Returned null activity zones for an activity without zones (should return an empty array)", zones);
				assertEquals("Returned an non-empty array of activity zones for an activity without zones", 0, zones.size());
			}
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaActivityZone zones} for an {@link StravaActivity} which doesn't exist
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code>
	 * </p>
	 * 
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityZones_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivityZone> zones = strava().listActivityZones(TestUtils.ACTIVITY_INVALID);

				assertNull("Returned non-null activity zones for an activity which doesn't exist", zones);
			}
		});
	}

	@Test
	public void testListActivityZones_privateActivity() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaActivityZone> zones = strava().listActivityZones(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
				assertNotNull(zones);
				assertEquals(0, zones.size());
			}
		});
	}

}
