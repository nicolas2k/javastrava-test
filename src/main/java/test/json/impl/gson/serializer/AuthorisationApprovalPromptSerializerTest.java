package test.json.impl.gson.serializer;

import javastrava.api.v3.auth.ref.AuthorisationApprovalPrompt;

/**
 * @author Dan Shannon
 *
 */
public class AuthorisationApprovalPromptSerializerTest extends EnumSerializerTest<AuthorisationApprovalPrompt> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<AuthorisationApprovalPrompt> getClassUnderTest() {
		return AuthorisationApprovalPrompt.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected AuthorisationApprovalPrompt getUnknownValue() {
		return AuthorisationApprovalPrompt.UNKNOWN;
	}

}
