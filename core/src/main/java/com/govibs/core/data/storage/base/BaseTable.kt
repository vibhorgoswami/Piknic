package com.govibs.core.data.storage.base

import android.provider.BaseColumns

abstract class BaseTable: BaseColumns {

    protected val FIELD_TYPE_TEXT = "TEXT"
    protected val FIELD_TYPE_INTEGER = "INTEGER"
    protected val FIELD_TYPE_DOUBLE = "DOUBLE"
    protected val FIELD_TYPE_NUMERIC = "Numeric"
    protected val FIELD_COMMA = ","
    protected val FIELD_SPACE = " "
    protected val FIELD_PRIMARY_KEY = "PRIMARY KEY"
    protected val FIELD_AUTO_INCREMENT = " AUTOINCREMENT"

}