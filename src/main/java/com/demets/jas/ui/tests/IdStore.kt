package com.demets.jas.ui.tests

import com.google.gson.annotations.SerializedName

/**
 * Store for identifiers of elements.
 *
 * @author Dmitry Emets <dmitriyemets@gmail.com>.
 */
data class IdStore(
        /**
         * Store for xpath of elements.
         */
        val xpaths: Map<String, String>,
        /**
         * Store for resource-id of elements.
         */
        @SerializedName("resource-ids")
        val resIds: Map<String, String>
)