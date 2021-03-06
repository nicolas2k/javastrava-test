package test.api.service.impl.activityservice;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class CreateCommentTest extends StravaTest {
	@Test
	public void testCreateComment_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaComment comment = null;
			try {
				comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_INVALID, "Test - ignore");
			} catch (final NotFoundException e1) {
				// Expected behaviour
				return;
			}

			// If it got added in error, delete it again
			try {
				stravaWithWriteAccess().deleteComment(comment);
			} catch (final NotFoundException e2) {
				// Ignore
			}
			fail("Added a comment to a non-existent activity");
		});
	}

	@Test
	public void testCreateComment_invalidComment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaComment comment = null;
			try {
				comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "");
			} catch (final IllegalArgumentException e1) {
				// Expected behaviour
				return;
			}

			// If it got added in error, delete it again
			try {
				stravaWithWriteAccess().deleteComment(comment);
			} catch (final NotFoundException e2) {
				// Ignore
			}
			fail("Added an invalid comment to an activity");
		});
	}

	@Test
	public void testCreateComment_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {

			StravaComment comment = null;
			try {
				comment = strava().createComment(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, "Test - ignore");
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			stravaWithWriteAccess().deleteComment(comment);
			fail("Created a comment despite not having write access");

		});
	}

	@Test
	public void testCreateComment_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaComment comment = null;
			try {
				comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, "Test - ignore");
			} catch (final UnauthorizedException e1) {
				// Expected
				return;
			}

			// If it got added in error, delete it again
			try {
				stravaWithWriteAccess().deleteComment(comment);
			} catch (final NotFoundException e2) {
				// Ignore
			}
			fail("Added a comment to a private activity");
		});
	}

	/**
	 * Can we create a comment on an activity flagged by the authenticated user as private, with view_private scope?
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateComment_privateActivityAuthenticatedUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaComment comment = null;
			try {
				comment = stravaWithViewPrivate().createComment(TestUtils.ACTIVITY_PRIVATE, "Test - ignore");
			} catch (final UnauthorizedException e) {
				// expected behaviour
				return;
			}
			stravaWithFullAccess().deleteComment(comment);
			fail("Created a comment with view_private, but not write scope");
		});
	}

	/**
	 * Can we create a comment on an activity flagged by the authenticated user as private, without view_private scope?
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateComment_privateActivityNoViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaComment comment = null;
			try {
				comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_PRIVATE, "Test - ignore");
			} catch (final UnauthorizedException e) {
				// expected behaviour
				return;
			}
			stravaWithFullAccess().deleteComment(comment);
			fail("Created a comment without view_private scope");
		});
	}

	@Test
	public void testCreateComment_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaComment comment = stravaWithWriteAccess().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
			StravaCommentTest.validateComment(comment);
			stravaWithWriteAccess().deleteComment(comment.getActivityId(), comment.getId());

		});
	}

}
