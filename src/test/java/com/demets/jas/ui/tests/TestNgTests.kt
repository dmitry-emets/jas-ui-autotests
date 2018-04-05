package com.demets.jas.ui.tests

import com.demets.jas.ui.tests.pages.MainPage
import com.demets.jas.ui.tests.pages.SettingsPage
import org.testng.annotations.Test


/**
 * Class that contains all test methods.
 *
 * @author Dmitry Emets <dmitriyemets@gmail.com>.
 */
class TestNgTests : TestNgTestBase() {

    /**
     * Test for application settings.
     */
    @Test(description = "Settings test")
    private fun testSettings() {
        val mainPage = MainPage(driver, "data/main_xpaths.json")
        val settingsPage = SettingsPage(driver, "data/settings_xpaths.json")

        mainPage.goToSettingsScreen()

        settingsPage.apply {
            testSwitch(ENABLE_SCROBBLING, true, "Scrobbling enabled", "Scrobbling disabled")
            testSeekBarDialog(MIN_PLAY_TIME, 1, 60, 30,
                    "%s seconds", "Minimum time set to %s seconds")
            testSeekBarDialog(MIN_PERCENT, 50, 100, 50,
                    "%s percents", "Minimum percent set to %s percents")
            testSeekBarDialog(MIN_DURATION, 1, 60, 30,
                    "%s seconds", "Minimum track duration set to %s seconds")
            testSwitch(ENABLE_NOTIFICATIONS, true, "Notifications enabled", "Notifications disabled")
            testSwitch(MIN_PRIORITY, true, "Minimum priority set", "Default priority set")
            testSwitch(ENABLE_TOAST_MESSAGE, false, "Toast message enabled", "Toast message disabled")
            backToMainScreen()
        }
    }

    companion object {
        private const val ENABLE_SCROBBLING = "Enable scrobbling"
        private const val MIN_PLAY_TIME = "Minimum play time to scrobble"
        private const val MIN_PERCENT = "Minimum percent of track duration to scrobble"
        private const val MIN_DURATION = "Minimum track duration"
        private const val ENABLE_NOTIFICATIONS = "Enable notifications"
        private const val MIN_PRIORITY = "Minimum priority notifications"
        private const val ENABLE_TOAST_MESSAGE = "Enable toast message on scrobble"
    }
}
