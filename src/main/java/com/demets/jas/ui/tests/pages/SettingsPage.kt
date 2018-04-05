package com.demets.jas.ui.tests.pages

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.AndroidTouchAction
import io.qameta.allure.Step
import org.openqa.selenium.WebElement
import org.testng.Assert

/**
 * PageObject for Settings screen.
 *
 * @author Dmitry Emets <dmitriyemets@gmail.com>.
 */
class SettingsPage(driver: AndroidDriver<WebElement>, pathToIds: String) : AbstractPage(driver, pathToIds) {

    @Step("Getting summary text for \"{name}\" setting")
    fun getSummary(name: String): String = getByXpath(X_ELEMENT_SUMMARY, name).text

    @Step("Click on \"{name}\" setting")
    fun clickElement(name: String) = getByXpath(X_ELEMENT, name).click()

    @Step("Getting switch value for \"{name}\" setting")
    fun getSwitchValue(name: String): Boolean = getByXpath(X_ELEMENT_SWITCH, name).getAttribute("checked") == "true"

    @Step("Click back button")
    fun backToMainScreen() = getByXpath(X_UP_BUTTON).click()


    @Step("Getting SeekBarDialog title")
    fun getSbTitle(): String = getById(I_SB_TITLE).text

    @Step("Getting SeekBarDialog summary")
    fun getSbSummary(): String = getById(I_SB_SUMMARY).text

    @Step("OK button existence check")
    fun isSbOkPresent(): Boolean = isPresentById(I_SB_OK)

    @Step("Cancel button existence check")
    fun isSbCancelPresent(): Boolean = isPresentById(I_SB_CANCEL)

    @Step("SeekBarDialog OK button click")
    fun clickSbOk() = getById(I_SB_OK).click()

    @Step("SeekBarDialog Cancel button click")
    fun clickSbCancel() = getById(I_SB_CANCEL).click()

    @Step("Checking \"{name}\" switch")
    fun testSwitch(name: String,
                   defaultValue: Boolean,
                   enabledSummary: String,
                   disabledSummary: String) {
        val defaultSummary = if (defaultValue) enabledSummary else disabledSummary
        val newSummary = if (defaultValue) disabledSummary else enabledSummary
        Assert.assertEquals(getSummary(name), defaultSummary)
        Assert.assertEquals(getSwitchValue(name), defaultValue)
        clickElement(name)
        Assert.assertEquals(getSummary(name), newSummary)
        Assert.assertEquals(getSwitchValue(name), !defaultValue)
    }

    @Step("Checking \"{name}\" seekbar")
    fun testSeekBarDialog(name: String,
                          min: Int,
                          max: Int,
                          defaultValue: Int,
                          hintTemplate: String,
                          summaryTemplate: String) {
        Assert.assertEquals(getSummary(name), String.format(summaryTemplate, defaultValue))
        clickElement(name)
        Assert.assertEquals(getSbTitle(), name)
        Assert.assertEquals(getSbSummary(), String.format(hintTemplate, defaultValue))
        Assert.assertTrue(isSbOkPresent())
        Assert.assertTrue(isSbCancelPresent())

        moveSeekbarTo(100)
        checkSeekbarHintAfterMove(100, min, max, hintTemplate)
        clickSbCancel()
        Assert.assertEquals(getSummary(name), String.format(summaryTemplate, defaultValue))

        clickElement(name)
        Assert.assertEquals(getSbSummary(), String.format(hintTemplate, defaultValue))
        moveSeekbarTo(100)
        checkSeekbarHintAfterMove(100, min, max, hintTemplate)
        clickSbOk()
        Assert.assertEquals(getSummary(name), String.format(summaryTemplate, max))
    }

    @Step("Check SeekBarDialogHint after move to {percent}%")
    private fun checkSeekbarHintAfterMove(percent: Int, min: Int, max: Int, hintTemplate: String) =
            Assert.assertEquals(getSbSummary(), String.format(hintTemplate, min + (max - min) * percent / 100))

    @Step("Move SeekBarDialogHint slider to {percent}%")
    private fun moveSeekbarTo(percent: Int) {
        val seekbar = getById(I_SB_SEEKBAR)
        val minX = seekbar.location.getX()
        val width = seekbar.size.getWidth()
        val y = seekbar.location.getY()
        val action = AndroidTouchAction(driver)

        action.press(minX, y)
                .moveTo(width * percent / 100, y)
                .release()
                .perform()
    }

    companion object {
        private const val X_ELEMENT = "Element"
        private const val X_ELEMENT_SUMMARY = "Element summary"
        private const val X_ELEMENT_SWITCH = "Switch"
        private const val X_UP_BUTTON = "Navigate up button"

        private const val I_SB_TITLE = "SeekBarDialog title"
        private const val I_SB_SEEKBAR = "SeekBarDialog seekbar"
        private const val I_SB_SUMMARY = "SeekBarDialog summary"
        private const val I_SB_CANCEL = "SeekBarDialog Cancel button"
        private const val I_SB_OK = "SeekBarDialog OK button"
    }
}