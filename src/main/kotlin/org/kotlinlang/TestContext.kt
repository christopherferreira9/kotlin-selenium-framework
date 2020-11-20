package org.kotlinlang

import org.openqa.selenium.remote.RemoteWebDriver

class TestContext(
    driver: Lazy<RemoteWebDriver>
) {
    val driver by driver
}