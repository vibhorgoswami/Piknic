package com.govibs.core.data.storage

import com.govibs.core.data.storage.base.BaseTable

object GroupTable: BaseTable() {
    val columnId = "id"
    val columnName = "name"
    val columnLunchTime = "lunchTime"
    val columnGroupLeader = "groupLeader"
    val tableName = "GroupTable"

    val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $tableName (" +
            "$columnId $FIELD_SPACE $FIELD_TYPE_INTEGER $FIELD_PRIMARY_KEY $FIELD_AUTO_INCREMENT" +
            "$FIELD_COMMA $columnLunchTime $FIELD_SPACE $FIELD_TYPE_NUMERIC" +
            "$FIELD_COMMA $columnGroupLeader $FIELD_SPACE $FIELD_TYPE_INTEGER" +
            "$FIELD_COMMA $columnName $FIELD_SPACE $FIELD_TYPE_TEXT);"
}