package org.kotlinlang.util


import org.kotlinlang.TestContext
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Store
import org.junit.jupiter.api.extension.InvocationInterceptor
import org.junit.jupiter.api.extension.TestWatcher
import org.junit.jupiter.api.extension.ReflectiveInvocationContext
import org.openqa.selenium.Cookie
import java.lang.reflect.Method

class SeleniumTestWatcher : TestWatcher, InvocationInterceptor {

    companion object {
        private const val TEST_CONTEXT_STORE_KEY = "testContext"
    }

    private fun closeDriver(context: ExtensionContext, success: Boolean) {
        val testContext = getStore(context)[TEST_CONTEXT_STORE_KEY] as? TestContext
        testContext?.driver?.run {
            val cookie = Cookie("zaleniumTestPassed", success.toString())
            manage().addCookie(cookie)
            quit()
        }
    }

    /*
    You can also report here to a test management tool
     */
    override fun testSuccessful(context: ExtensionContext) {
        closeDriver(context, true)
    }

    /*
    You can also report here to a test management tool
     */
    override fun testFailed(context: ExtensionContext, cause: Throwable?) {
        closeDriver(context, false)
    }

    private fun getStore(context: ExtensionContext): Store {
        return context.getStore(ExtensionContext.Namespace.create(javaClass, context.requiredTestMethod))
    }

    override fun interceptTestTemplateMethod(
        invocation: InvocationInterceptor.Invocation<Void>,
        invocationContext: ReflectiveInvocationContext<Method>,
        extensionContext: ExtensionContext
    ) {
        val context = invocationContext.arguments
            .firstOrNull { it is TestContext } as? TestContext
        context?.also {
            getStore(extensionContext).put(TEST_CONTEXT_STORE_KEY, it)
        }
        invocation.proceed()
    }

}