package com.pharmacy.models

class CustomerModel {
    var id: Int = 0
    var code: String? = null
    var phone: String? = null
    var conditions: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var middleName: String? = null
    val name: String
        get() = "$firstName $middleName $lastName"
}
