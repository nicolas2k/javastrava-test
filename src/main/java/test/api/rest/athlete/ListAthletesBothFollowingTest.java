package test.api.rest.athlete;

import java.util.Arrays;

import javastrava.api.v3.model.StravaAthlete;
import test.api.model.StravaAthleteTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue83;
import test.utils.TestUtils;

public class ListAthletesBothFollowingTest extends APIListTest<StravaAthlete, Integer> {
	/**
	 *
	 */
	public ListAthletesBothFollowingTest() {
		this.listCallback = (api, id) -> api.listAthletesBothFollowing(id, null, null);
		this.pagingCallback = (paging) -> api().listAthletesBothFollowing(validId(), paging.getPage(),
				paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ATHLETE_INVALID_ID;
	}

	// 3. Private athlete
	@Override
	public void list_privateBelongsToOtherUser() throws Exception {
		// TODO This is a workaround for issue javastravav3api#83
		if (new Issue83().isIssue()) {
			return;
		}
		// End of workaround
		super.list_privateBelongsToOtherUser();
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.ATHLETE_PRIVATE_ID;
	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		StravaAthleteTest.validateAthlete(athlete);
	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaAthlete[] list) {
		StravaAthleteTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ATHLETE_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

}
