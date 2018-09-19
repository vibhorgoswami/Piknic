package com.govibs.core.data.storage

import com.govibs.core.data.storage.base.BaseTable

object ContactTable: BaseTable() {

    val columnId = "id"
    val columnName = "name"
    val columnGroupId = "groupId"
    val columnUri = "avatarUri"
    val columnEmail = "email"
    val columnPhone = "phone"
    val tableName = "ContactTable"

    val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $tableName( " +
            "$columnId $FIELD_SPACE $FIELD_TYPE_INTEGER $FIELD_PRIMARY_KEY $FIELD_AUTO_INCREMENT " +
            "$FIELD_COMMA $columnName $FIELD_SPACE $FIELD_TYPE_TEXT " +
            "$FIELD_COMMA $columnGroupId $FIELD_SPACE $FIELD_TYPE_INTEGER " +
            "$FIELD_COMMA $columnEmail $FIELD_SPACE $FIELD_TYPE_TEXT " +
            "$FIELD_COMMA $columnPhone $FIELD_SPACE $FIELD_TYPE_TEXT " +
            "$FIELD_COMMA $columnUri $FIELD_SPACE $FIELD_TYPE_TEXT)"

}