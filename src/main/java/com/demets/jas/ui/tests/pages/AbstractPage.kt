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
    /**
     * Store for ids of elements.
     */
    private val idStore: IdStore

    /**
     * Initialization of ids store.
     */
    init {
        val reader = InputStreamReader(FileInputStream(pathToIds), "UTF-8")
        idStore = Gson().fromJson<IdStore>(reader, IdStore::class.java)
    }

    /**
     * Returns WebElement by it's key in predefined map.
     *
     * @param key       Key of desired xpath template.
     * @param params    Parameters for xpath template.
     * @return  WebElement.
     */
    fun getByXpath(key: String, vararg params: String): WebElement {
        return if (idStore.xpaths.containsKey(key)) {
            val xpath = String.format(idStore.xpaths[key]!!, *params)
            driver.findElementByXPath(xpath)
        } else {
            throw RuntimeException("Key \"$key\" not found in xpath store.")
        }
    }

    /**
     * Returns WebElement by it's key in predefined map.
     *
     * @param key       Key of desired resource-id template.
     * @param params    Parameters for resource-id template.
     * @return  WebElement.
     */
    fun getById(key: String, vararg params: String): WebElement {
        return if (idStore.resIds.containsKey(key)) {
            val resId = String.format(idStore.resIds[key]!!, *params)
            driver.findElementById(resId)
        } else {
            throw RuntimeException("Key \"$key\" not found in resource-id store.")
        }
    }

    /**
     * Returns is element present on page.
     *
     * @param key       Key of desired resource-id template.
     * @param params    Parameters for resource-id template.
     * @return  Is element present.
     */
    fun isPresentById(key: String, vararg params: String): Boolean {
        return if (idStore.resIds.containsKey(key)) {
            val resId = String.format(idStore.resIds[key]!!, *params)
            driver.findElementsById(resId).isNotEmpty()
        } else {
            false
        }
    }
}