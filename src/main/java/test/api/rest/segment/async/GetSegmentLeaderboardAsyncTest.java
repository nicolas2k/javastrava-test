/**
 *
 */
package test.api.rest.segment.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaSegmentLeaderboard;
import javastrava.api.v3.model.StravaSegmentLeaderboardEntry;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.util.Paging;
import test.api.model.StravaSegmentLeaderboardEntryTest;
import test.api.model.StravaSegmentLeaderboardTest;
import test.api.rest.segment.GetSegmentLeaderboardTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shanon
 *
 */
public class GetSegmentLeaderboardAsyncTest extends GetSegmentLeaderboardTest {
	/**
	 * Constructor sets the callback to use
	 */
	public GetSegmentLeaderboardAsyncTest() {
		this.getCallback = (api, id) -> api.getSegmentLeaderboardAsync(id, null, null, null, null, null, null, null, null, null).get();
	}

	/**
	 * @see test.api.rest.util.ArrayCallback#getArray(javastrava.util.Paging)
	 */
	@Override
	public StravaSegmentLeaderboardEntry[] getArray(final Paging paging) throws Exception {
		final List<StravaSegmentLeaderboardEntry> list = api()
				.getSegmentLeaderboardAsync(validId(), null, null, null, null, null, null, paging.getPage(), paging.getPageSize(), 0).get().getEntries();
		final StravaSegmentLeaderboardEntry[] array = new StravaSegmentLeaderboardEntry[list.size()];
		int i = 0;
		for (final StravaSegmentLeaderboardEntry entry : list) {
			array[i] = entry;
			i++;
		}
		return array;
	}

	// 4. Filter by age group
	@Override
	public void testGetSegmentLeaderboard_filterByAgeGroup() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, null, StravaAgeGroup.AGE35_44, null,
					null, null, null, null, null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 10. Filter by ALL options combined
	@Override
	public void testGetSegmentLeaderboard_filterByAllOptions() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, StravaGender.MALE,
					StravaAgeGroup.AGE45_54, StravaWeightClass.KG85_94, Boolean.FALSE, TestUtils.CLUB_VALID_ID, StravaLeaderboardDateRange.THIS_YEAR, null,
					null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 7. Filter by valid club
	@Override
	public void testGetSegmentLeaderboard_filterByClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, null, null, null, null,
					TestUtils.CLUB_VALID_ID, null, null, null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 6. Filter by athletes the authenticated user is following
	@Override
	public void testGetSegmentLeaderboard_filterByFollowing() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, null, null, null, Boolean.TRUE, null,
					null, null, null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 3. Filter by gender
	@Override
	public void testGetSegmentLeaderboard_filterByGender() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, StravaGender.FEMALE, null, null, null,
					null, null, null, null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			for (final StravaSegmentLeaderboardEntry entry : leaderboard.getEntries()) {
				assertEquals(StravaGender.FEMALE, entry.getAthleteGender());
				StravaSegmentLeaderboardEntryTest.validate(entry);
			}
		});
	}

	// 8. Filter by invalid club
	@Override
	public void testGetSegmentLeaderboard_filterByInvalidClub() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, null, null, null, null, TestUtils.CLUB_INVALID_ID, null, null, null, null).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Got leaderboard for an invalid club!");
		});
	}

	// 9. Filter by leaderboard date range
	@Override
	public void testGetSegmentLeaderboard_filterByLeaderboardDateRange() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, null, null, null, null, null,
					StravaLeaderboardDateRange.THIS_YEAR, null, null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	// 5. Filter by weight class
	@Override
	public void testGetSegmentLeaderboard_filterByWeightClass() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboard leaderboard = api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_VALID_ID, null, null, StravaWeightClass.KG75_84,
					null, null, null, null, null, null).get();
			assertNotNull(leaderboard);
			assertFalse(leaderboard.getEntries().isEmpty());
			StravaSegmentLeaderboardTest.validate(leaderboard);
		});
	}

	@Override
	public void testGetSegmentLeaderboard_hazardousSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().getSegmentLeaderboardAsync(TestUtils.SEGMENT_HAZARDOUS_ID, null, null, null, null, null, null, null, null, null).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Returned leaderboard for a segment flagged as hazardous");
		});
	}

	@Override
	public void testPageNumberAndSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegmentLeaderboardEntry[] bothPages = getArray(new Paging(1, 2));
			assertNotNull(bothPages);
			assertEquals(3, bothPages.length);
			validateArray(bothPages);
			final StravaSegmentLeaderboardEntry[] firstPage = getArray(new Paging(1, 1));
			assertNotNull(firstPage);
			assertEquals(2, firstPage.length);
			validateArray(firstPage);
			final StravaSegmentLeaderboardEntry[] secondPage = getArray(new Paging(2, 1));
			assertNotNull(secondPage);
			assertEquals(2, secondPage.length);
			validateArray(secondPage);

			// The first entry in bothPages should be the same as the first
			// entry in firstPage
				assertEquals(bothPages[0], firstPage[0]);

				// The second entry in bothPages should be the same as the first
				// entry in secondPage
				assertEquals(bothPages[1], secondPage[0]);

			});
	}

	@Override
	public void testPageSize() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get a list with only one entry
				final StravaSegmentLeaderboardEntry[] list = getArray(new Paging(1, 1));
				assertNotNull(list);
				assertEquals(2, list.length);

				// Validate all the entries in the list
				validateArray(list);
			});
	}

	@Override
	public void testPagingOutOfRangeHigh() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the 200,000,000th entry in the list - this is pretty unlikely
			// to return anything!
				final StravaSegmentLeaderboardEntry[] list = getArray(new Paging(1000000, 200));

				assertNotNull(list);
				assertEquals(1, list.length);
			});
	}

}
