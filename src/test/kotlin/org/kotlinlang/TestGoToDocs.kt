package org.kotlinlang

import org.kotlinlang.pages.HomePage
import io.qameta.allure.Description
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@Story("Test Navigating to the Kotlin Docs")
class TestGoToDocs : TestBase() {

    @DisplayName("Test Kotlin Docs")
    @Description("This test ensures the docs link in the header of the Kotlin page works")
    @Severity(SeverityLevel.CRITICAL)
    @ParameterizedTest
    @MethodSource("testContextProvider")
    fun testGoToDocs(testContext: TestContext) {
        HomePage(testContext).apply {
            clickHeaderLink()
            verifyCurrentUrl()
        }
    }
}