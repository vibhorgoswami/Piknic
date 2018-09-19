package com.govibs.core.data.model

import android.graphics.drawable.Drawable
import android.net.Uri
import com.pchmn.materialchips.model.ChipInterface
import org.apache.commons.lang3.StringUtils

data class ContactModel(var name: String?): ChipInterface {

    private var groupId: Int = 0
    private var id:Int = 0
    private var email:String? = null
    private var address:String? = null
    private var phoneNo: String? = null
    private var avatarUri: Uri? = null

    fun setId(id: Int) {
        this.id = id
    }

    fun getGroupId(): Int {
        return groupId
    }

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun setPhoneNo(phoneNo: String?) {
        this.phoneNo = phoneNo
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun setAvatarUri(avatarUri: String?) {
        if (StringUtils.isNotEmpty(avatarUri) && StringUtils.isNotBlank(avatarUri)) {
            this.avatarUri = Uri.parse(avatarUri)
        }
    }

    override fun getId(): Any {
        return id
    }

    override fun getAvatarUri(): Uri? {
        return avatarUri
    }

    override fun getAvatarDrawable(): Drawable? {
        return null
    }

    override fun getLabel(): String? {
        return name
    }

    override fun getInfo(): String? {
        return phoneNo
    }

    override fun equals(other: Any?): Boolean {
        if (other is ContactModel) {
            return other.id == this@ContactModel.id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}