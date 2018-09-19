package com.govibs.core.data.model

data class GroupModel(var groupName: String?) {

    private var groupId: Int = 0
    private var contactModelList: ArrayList<ContactModel> = ArrayList(1)
    private var lunchTime: Long = 0
    private var groupLeader: Int = 0

    fun getGroupId(): Int {
        return groupId
    }

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
    }

    fun getContactModelList(): ArrayList<ContactModel> {
        return contactModelList
    }

    fun setContactModelList(contactModelList: ArrayList<ContactModel>) {
        this.contactModelList = contactModelList
    }

    fun getLunchTime(): Long {
        return lunchTime
    }

    fun setLunchTime(lunchTime: Long) {
        this.lunchTime = lunchTime
    }

    fun getGroupLeader(): Int {
        return groupLeader
    }

    fun setGroupLeader(groupLeader: Int) {
        this.groupLeader = groupLeader
    }
}