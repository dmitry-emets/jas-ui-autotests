package com.demets.jas.ui.tests.pages

import com.demets.jas.ui.tests.IdStore
import com.google.gson.Gson
import io.appium.java_client.android.AndroidDriver
import org.openqa.selenium.WebElement
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Base class for all PageObjects.
 *
 * @author Dmitry Emets <dmitriyemets@gmail.com>.
 */
abstract class AbstractPage(protected val driver: AndroidDriver<WebElement>, pathToIds: String) {
    private val idStore: IdStore

    init {
        val reader = InputStreamReader(FileInputStream(pathToIds), "UTF-8")
        idStore = Gson().fromJson<IdStore>(reader, IdStore::class.java)
    }

    fun getByXpath(key: String, vararg params: String): WebElement {
        return if (idStore.xpaths.containsKey(key)) {
            val xpath = String.format(idStore.xpaths[key]!!, *params)
            driver.findElementByXPath(xpath)
        } else {
            throw RuntimeException("Key \"$key\" not found in xpath store.")
        }
    }

    fun getById(key: String, vararg params: String): WebElement {
        return if (idStore.resIds.containsKey(key)) {
            val resId = String.format(idStore.resIds[key]!!, *params)
            driver.findElementById(resId)
        } else {
            throw RuntimeException("Key \"$key\" not found in resource-id store.")
        }
    }

    fun isPresentById(key: String, vararg params: String): Boolean {
        return if (idStore.resIds.containsKey(key)) {
            val resId = String.format(idStore.resIds[key]!!, *params)
            driver.findElementsById(resId).isNotEmpty()
        } else {
            false
        }
    }
}