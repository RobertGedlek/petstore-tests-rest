package info.gedlek.suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("info.gedlek.tests")
@IncludeTags("positive")
public class SmokeTestSuite {

}