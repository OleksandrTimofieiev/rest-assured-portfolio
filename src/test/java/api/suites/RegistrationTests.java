package api.suites;

import org.junit.platform.suite.api.*;

@Suite
@SelectPackages("api.tests")
@IncludeTags("registration")
public class RegistrationTests {
}
