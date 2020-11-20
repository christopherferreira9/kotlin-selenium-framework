package org.kotlinlang.util

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

fun <T : WebElement> WebDriver.scrollToElement(
    element: T?, action: T.() -> Unit = {}
): WebDriver =
    (this as RemoteWebDriver).apply {
        element?.let {
            executeScript(
                "arguments[0].scrollIntoView(true)", it
            )
            Actions(this).moveToElement(it)
            it.action()
        }
    }

fun <T> WebDriver.waitUntil(
    condition: ExpectedCondition<T>,
    timeOutInSeconds: Long,
    block: T.() -> Unit = {}
): WebDriver {
    val result = WebDriverWait(this, timeOutInSeconds)
        .until(condition)
    return apply { result.block() }
}

fun WebDriver.waitUntilClickable(
    element: WebElement?, timeOutInSeconds: Long,
    block: WebElement.() -> Unit
): WebDriver =
    waitUntil(
        ExpectedConditions.elementToBeClickable(element),
        timeOutInSeconds,
        block
    )

fun WebDriver.waitUntilTextPresent(
    element: WebElement?, text: String, timeOutInSeconds: Long
): WebDriver =
    waitUntil(
        ExpectedConditions.textToBePresentInElement(element, text),
        timeOutInSeconds
    )