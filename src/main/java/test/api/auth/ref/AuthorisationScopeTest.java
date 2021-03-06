package test.api.auth.ref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.auth.ref.AuthorisationScope;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class AuthorisationScopeTest {
	@Test
	public void testGetDescription() {
		for (final AuthorisationScope type : AuthorisationScope.values()) {
			assertNotNull(type.getDescription());
		}
	}

	@Test
	public void testGetId() {
		for (final AuthorisationScope type : AuthorisationScope.values()) {
			assertNotNull(type.getId());
			assertEquals(type, AuthorisationScope.create(type.getId()));
		}
	}

}
