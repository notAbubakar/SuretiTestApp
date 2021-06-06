package com.bucks.suretitestapp.api

data class ActiveChecksDto(
    val userInfo: ActiveChecksUserInfo,
    val checkNo: String,
    val checkAmount: Double,
    val claimNo: String,
    val loannumber: String,
    val preferredcontractor: String,
    val branch: ActiveChecksBranch,
    val addressofloss: String,
    val collabortator: List<ActiveChecksCollaborator>
)