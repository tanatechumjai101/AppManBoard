package com.example.thefirstnewprojectaddtoday28jan62.compare

import java.text.SimpleDateFormat

class StringDateComparator : Comparator<String> {

    var dateFormat = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss")

    override fun compare(lhs: String, rhs: String): Int {
        return dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs))
    }
}
