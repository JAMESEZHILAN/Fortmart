package com.base.app.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.base.app.shared.Constants.DEFAULT_INT
import com.base.app.shared.Constants.DEFAULT_LNG
import com.base.app.shared.Constants.DEFAULT_STR
import java.io.Serializable


@Entity
data class UserDetails(
    @PrimaryKey
    val id: Int? = DEFAULT_INT,
    val firstName: String? = DEFAULT_STR,
    val lastName: String? = DEFAULT_STR,
    val countryCode: String? = DEFAULT_STR,
    val dob: String? = DEFAULT_STR,
    val phone: Long? = DEFAULT_LNG,
    val email: String? = DEFAULT_STR,
    val password: String? = DEFAULT_STR,
    val roleId: Int? = DEFAULT_INT,
    val userCode: Int? = DEFAULT_INT,
    val referalCode: Int? = DEFAULT_INT,
    val guid: Int? = DEFAULT_INT,
    val image: String? = DEFAULT_STR,
    val preferences: String? = DEFAULT_STR,
    val address: String? = DEFAULT_STR,
    @Embedded
    val businessInfo: BusinessInfo? = BusinessInfo(),
    val documents: String? = DEFAULT_STR,
    val commissionId: Int? = DEFAULT_INT,
    val active: Boolean? = true,
    val otp: Int? = DEFAULT_INT,
    val forgotPasswordHash: String? = DEFAULT_STR,
    val createdAt: String? = DEFAULT_STR,
    val updatedAt: String? = DEFAULT_STR,
    val token: String? = DEFAULT_STR
): Serializable

data class BusinessInfo(
    val businessName: String? = DEFAULT_STR
): Serializable