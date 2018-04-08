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

    /**
     * Returns summary name for [name] setting.
     *
     * @param name  Setting's name.
     * @return  Summary text.
     */
    @Step("Getting summary text for \"{name}\" setting")
    fun getSummary(name: String): String = getByXpath(X_ELEMENT_SUMMARY, name).text

    /**
     * Performs click on [name] item.
     *
     * @param name  Setting's name.
     */
    @Step("Click on \"{name}\" setting")
    fun clickElement(name: String) = getByXpath(X_ELEMENT, name).click()

    /**
     * Returns current state of switch of [name] setting.
     *
     * @param name  Setting's name.
     * @return  Is switch in "true" state.
     */
    @Step("Getting switch value for \"{name}\" setting")
    fun getSwitchValue(name: String): Boolean = getByXpath(X_ELEMENT_SWITCH, name).getAttribute("checked") == "true"

    /**
     * Performs click on back button in settings.
     */
    @Step("Click back button")
    fun backToMainScreen() = getByXpath(X_UP_BUTTON).click()


    //Methods for SeekBarDialog popup.
    /**
     * Returns title of SeekBarDialog.
     *
     * @return  Title of SeekBarDialog.
     */
    @Step("Getting SeekBarDialog title")
    private fun getSbTitle(): String = getById(I_SB_TITLE).text

    /**
     * Returns SeekBarDialog hint text.
     *
     * @return  Hint text.
     */
    @Step("Getting SeekBarDialog summary")
    private fun getSbHint(): String = getById(I_SB_HINT).text

    /**
     * Returns OK button existence.
     *
     * @return  Is OK button present.
     */
    @Step("OK button existence check")
    private fun isSbOkPresent(): Boolean = isPresentById(I_SB_OK)

    /**
     * Returns Cancel button existence.
     *
     * @return  Is cancel button present.
     */
    @Step("Cancel button existence check")
    private fun isSbCancelPresent(): Boolean = isPresentById(I_SB_CANCEL)

    /**
     * Performs click on OK button.
     */
    @Step("SeekBarDialog OK button click")
    private fun clickSbOk() = getById(I_SB_OK).click()

    /**
     * Performs click on Cancel button.
     */
    @Step("SeekBarDialog Cancel button click")
    private fun clickSbCancel() = getById(I_SB_CANCEL).click()

    /**
     * Performs test for switch preference.
     * Checks default state and summary, then toggles switch and checks new state and summary.
     *
     * @param name              Setting's name.
     * @param defaultValue      Default value of switch.
     * @param enabledSummary    Summary for switch in "true" state.
     * @param disabledSummary   Summary for switch in "false" state.
     */
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

    /**
     * Performs test for SeekBarDialog preference. It performs following steps:
     * Checks default state and summary.
     * Checks popup window.
     * Moves slider to max, checks hint and clicks Cancel.
     * Checks summary.
     * Checks popup window.
     * Moves slider to max, checks hint and clicks OK.
     * Checks summary.
     *
     * @param name              Setting's name.
     * @param min               Min possible value for preference.
     * @param max               Max possible value for preference.
     * @param defaultValue      Default value for preference.
     * @param hintTemplate      Hint template for popup window.
     * @param summaryTemplate   Template for preference's summary.
     */
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
        Assert.assertEquals(getSbHint(), String.format(hintTemplate, defaultValue))
        Assert.assertTrue(isSbOkPresent())
        Assert.assertTrue(isSbCancelPresent())

        moveSeekbarTo(100)
        checkSeekbarHintAfterMove(100, min, max, hintTemplate)
        clickSbCancel()
        Assert.assertEquals(getSummary(name), String.format(summaryTemplate, defaultValue))

        clickElement(name)
        Assert.assertEquals(getSbHint(), String.format(hintTemplate, defaultValue))
        moveSeekbarTo(100)
        checkSeekbarHintAfterMove(100, min, max, hintTemplate)
        clickSbOk()
        Assert.assertEquals(getSummary(name), String.format(summaryTemplate, max))
    }

    /**
     * Checks popup hint after move to [percent].
     *
     * @param percent       New SeekBar value (In percents of slider length!).
     * @param min           Min slider value.
     * @param max           Max slider value.
     * @param hintTemplate  Template for SeekBarDialog hint..
     */
    @Step("Check SeekBarDialog hint after move to {percent}%")
    private fun checkSeekbarHintAfterMove(percent: Int, min: Int, max: Int, hintTemplate: String) =
            Assert.assertEquals(getSbHint(), String.format(hintTemplate, min + (max - min) * percent / 100))

    /**
     * Moves SeekBarDialog slider to desired percent.
     *
     * @param percent   Desired percent (In percents of slider length!).
     */
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
        private const val I_SB_HINT = "SeekBarDialog summary"
        private const val I_SB_CANCEL = "SeekBarDialog Cancel button"
        private const val I_SB_OK = "SeekBarDialog OK button"
    }
}