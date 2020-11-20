package org.kotlinlang

import org.kotlinlang.util.SeleniumTestWatcher
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger { }

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SeleniumTestWatcher::class)
abstract class TestBase {

    lateinit var name: String

    lateinit var testClass: String

    companion object {
        private const val TEST_CONFIG_PROPERTIES = "config.properties"
        const val GRID_URL_PROPERTY = "gridUrl"
        const val BROWSER_PROPERTY = "browser"
        const val URL = "kotlinUrl"
    }

    val properties by lazy {
        this::class.java.classLoader
            .getResourceAsStream(TEST_CONFIG_PROPERTIES)!!.use {
                Properties(System.getProperties()).apply { load(it) }
            }
    }

    val browsers: List<String> by lazy { properties.getProperty(BROWSER_PROPERTY).split(",") }

    val gridUrl: String by lazy { properties.getProperty(GRID_URL_PROPERTY) }

    val url: String by lazy { properties.getProperty(URL) }

    @BeforeAll
    fun setUp(testInfo: TestInfo) {
        testClass = testInfo.testClass.get().simpleName
    }

    @BeforeEach
    fun init(testInfo: TestInfo) {
        name = testInfo.testMethod.get().name
    }

    private fun buildDriver(browser: String) = lazy {
        val uuid = UUID.randomUUID()
        logger.info { "Execution random uuid: $uuid browser: $browser - testClass: $testClass" }

        val cap = DesiredCapabilities().apply {
            browserName = browser
            setCapability(
                "name",
                "$browserName | $testClass | $uuid"
            )
        }
        logger.info { "Caps: $cap" }
        logger.info { "Executing for url: $url" }
        logger.info { "GRID: $gridUrl" }

        RemoteWebDriver(URL(gridUrl), cap).apply {
            manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
            manage().window().maximize()
            get(
                URI(
                    url
                ).toString()
            )
        }
    }

    fun testContextProvider() =
        browsers.map { b ->
                TestContext(
                    driver = buildDriver(b)
                )
        }
}