package org.kotlinlang.pages

import io.qameta.allure.Step
import org.kotlinlang.TestContext
import org.kotlinlang.util.waitUntilClickable
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class HomePage(private val testContext: TestContext) {
    @FindBy(xpath = "//span[text()[contains(.,'Docs')]]")
    private val docsHeaderLink: WebElement? = null

    @FindBy(xpath = "//a[text()[contains(., 'Kotlin')]]")
    private val kotlinDropdownOption: WebElement? = null

    init {
        PageFactory.initElements(testContext.driver, this)
    }

    @Step("Check the Header title")
    fun clickHeaderLink() {
        testContext.driver
            .waitUntilClickable(docsHeaderLink, 10) {
                click()
            }
            .waitUntilClickable(kotlinDropdownOption, 10) {
                click()
            }
        assert(testContext.driver.currentUrl == "https://kotlinlang.org/docs/home.html")
    }

    @Step("Verify Current url is equal to https://kotlinlang.org/docs/home.html")
    fun verifyCurrentUrl() {
        assert(testContext.driver.currentUrl.toString() == "https://kotlinlang.org/docs/home.html")
    }
}