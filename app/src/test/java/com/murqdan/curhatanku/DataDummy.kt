package com.murqdan.curhatanku

import com.murqdan.curhatanku.response.ListCurhatanItem

object DataDummy {
    fun generateDummyCurhatanResponse(): List<ListCurhatanItem> {
        val items: MutableList<ListCurhatanItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListCurhatanItem(
                i.toString(),
                "url $i",
                "name + $i",
                "description $i",
                0.0,
                0.0
            )
            items.add(quote)
        }
        return items
    }
}