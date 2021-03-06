package com.demets.jas.ui.tests

import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import java.net.URL

/**
 * Base class for TestNG-based test classes.
 *
 * @author Dmitry Emets <dmitriyemets@gmail.com>.
 */
open class TestNgTestBase {
    protected lateinit var driver: AndroidDriver<WebElement>

    @BeforeMethod
    fun initTestSuite() {
        val capabilities = DesiredCapabilities()
        capabilities.apply {
            setCapability("platformName", "Android")
            setCapability("automationName", "Appium")
            setCapability("platformVersion", "8.1")
            setCapability("deviceName", "Android Emulator")
            setCapability("appActivity", ".MainActivity")
            setCapability("appPackage", "com.demets.jas")
        }
        val url = System.getProperty("url")
        Assert.assertNotNull(url)
        val serverAddress = URL(url)
        driver = AndroidDriver(serverAddress, capabilities)
    }

    @AfterMethod(alwaysRun = true)
    fun tearDown() {
        driver.quit()
    }
}
