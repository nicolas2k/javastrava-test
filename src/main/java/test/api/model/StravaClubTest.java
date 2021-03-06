package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaClub;
import javastrava.api.v3.model.reference.StravaClubType;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaClubTest extends BeanTest<StravaClub> {

	public static void validate(final StravaClub club) {
		assertNotNull(club);
		validate(club, club.getId(), club.getResourceState());
	}

	public static void validate(final StravaClub club, final Integer id, final StravaResourceState state) {
		assertNotNull(club);
		assertNotNull(club.getId());
		assertNotNull(club.getResourceState());
		assertEquals(id, club.getId());
		assertEquals(state, club.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(club.getCity());
			assertNotNull(club.getClubType());
			assertFalse(club.equals(StravaClubType.UNKNOWN));
			assertNotNull(club.getCountry());
			// Optional
			// assertNotNull(club.getDescription());
			assertNotNull(club.getMemberCount());
			assertTrue(club.getMemberCount() >= 0);
			assertNotNull(club.getName());
			assertNotNull(club.getPrivateClub());
			assertNotNull(club.getProfile());
			assertNotNull(club.getProfileMedium());
			assertNotNull(club.getSportType());
			assertNotNull(club.getState());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNull(club.getCity());
			assertNull(club.getClubType());
			assertNull(club.getCountry());
			// Optional
			// assertNull(club.getDescription());
			assertNull(club.getMemberCount());
			assertNotNull(club.getName());
			assertNull(club.getPrivateClub());
			assertNotNull(club.getProfile());
			assertNotNull(club.getProfileMedium());
			assertNull(club.getSportType());
			assertNull(club.getState());
			return;
		}
		if ((state == StravaResourceState.META) || (state == StravaResourceState.PRIVATE)) {
			return;
		}
		fail("Unexpected state " + state + " for club " + club);
	}

	/**
	 * @param asList
	 */
	public static void validateList(final List<StravaClub> asList) {
		for (final StravaClub club : asList) {
			validate(club);
		}

	}

	@Override
	protected Class<StravaClub> getClassUnderTest() {
		return StravaClub.class;
	}

	/**
	 * @param clubs
	 *            List of clubs to check
	 * @param id
	 *            Id of the club we're checking for membership
	 * @return <code>true</code> if one of the clubs has the given id
	 */
	public static boolean checkIsMember(final StravaClub[] clubs, final Integer id) {
		for (final StravaClub club : clubs) {
			if (club.getId().intValue() == id.intValue()) {
				return true;
			}
		}
		return false;
	}
}
