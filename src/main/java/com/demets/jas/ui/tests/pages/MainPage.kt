package com.demets.jas.ui.tests.pages

import io.appium.java_client.android.AndroidDriver
import io.qameta.allure.Step
import org.openqa.selenium.WebElement

/**
 * PageObject for Main screen.
 *
 * @author Dmitry Emets <dmitriyemets@gmail.com>.
 */
class MainPage(driver: AndroidDriver<WebElement>, pathToIds: String) : AbstractPage(driver, pathToIds) {

    /**
     * Performs click on Settings button in title.
     */
    @Step("Select \"Settings\" menu item")
    fun goToSettingsScreen() = getById(I_SB_TITLE).click()

    companion object {
        private const val I_SB_TITLE = "Settings button"
    }
}