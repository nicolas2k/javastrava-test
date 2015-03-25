package test.api.service.impl.athleteservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaSegmentEffortTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListAthleteKOMsTest extends PagingListMethodTest<StravaSegmentEffort, Long> {
	// Test cases
	// 1. Valid athlete with some KOM's
	@Test
	public void testListAthleteKOMs_withKOM() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> koms = strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID);
				assertNotNull(koms);
				assertFalse(koms.size() == 0);
				for (final StravaSegmentEffort effort : koms) {
					StravaSegmentEffortTest.validateSegmentEffort(effort);
				}
			}
		});
	}

	// 2. Valid athlete with no KOM's
	@Test
	public void testListAthleteKOMs_withoutKOM() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> koms = strava().listAthleteKOMs(TestUtils.ATHLETE_WITHOUT_KOMS);
				assertNotNull(koms);
				assertTrue(koms.size() == 0);
			}
		});
	}

	// 3. Invalid athlete
	@Test
	public void testListAthleteKOMs_invalidAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				List<StravaSegmentEffort> koms = null;
				koms = strava().listAthleteKOMs(TestUtils.ATHLETE_INVALID_ID);

				assertNull(koms);
			}
		});
	}

	// 4. Private athlete
	public void testListAthleteKOMs_privateAthlete() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegmentEffort> koms = strava().listAthleteKOMs(TestUtils.ATHLETE_PRIVATE_ID);
				assertNotNull(koms);
				assertTrue(koms.isEmpty());
			}
		});
	}

	@Override
	protected void validate(final StravaSegmentEffort effort, final Long id, final StravaResourceState state) {
		StravaSegmentEffortTest.validateSegmentEffort(effort, id, state);

	}

	@Override
	protected void validate(final StravaSegmentEffort effort) {
		validate(effort, effort.getId(), effort.getResourceState());

	}

	@Override
	protected ListCallback<StravaSegmentEffort> callback() {
		return (new ListCallback<StravaSegmentEffort>() {

			@Override
			public List<StravaSegmentEffort> getList(final Paging paging) {
				return strava().listAthleteKOMs(TestUtils.ATHLETE_AUTHENTICATED_ID, paging);
			}

		});
	}

}