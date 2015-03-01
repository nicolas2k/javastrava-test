package test.api.service.impl.retrofit.athleteservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.impl.retrofit.AthleteServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaAthleteTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteFriendsTest extends PagingListMethodTest<StravaAthlete, Integer> {
	@Test
	public void testListAuthenticatedAthleteFriends_friends() {
		final List<StravaAthlete> friends = service().listAuthenticatedAthleteFriends();
		assertNotNull(friends);
		assertFalse(friends.isEmpty());
		for (final StravaAthlete athlete : friends) {
			StravaAthleteTest.validateAthlete(athlete, athlete.getId(), StravaResourceState.SUMMARY);
		}
	}

	private AthleteServices service() {
		return AthleteServicesImpl.implementation(TestUtils.getValidToken());
	}

	@Override
	protected void validate(final StravaAthlete athlete, final Integer id, final StravaResourceState state) {
		StravaAthleteTest.validateAthlete(athlete, id, state);

	}

	@Override
	protected void validate(final StravaAthlete athlete) {
		validate(athlete, athlete.getId(), athlete.getResourceState());

	}

	@Override
	protected ListCallback<StravaAthlete> callback() {
		return (new ListCallback<StravaAthlete>() {

			@Override
			public List<StravaAthlete> getList(final Paging paging) {
				return service().listAuthenticatedAthleteFriends(paging);
			}

		});
	}

}
