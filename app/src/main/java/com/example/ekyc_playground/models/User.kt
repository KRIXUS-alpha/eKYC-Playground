package com.example.ekyc_playground.models

data class User(
    var Email:String = "",
    var Address:String= "",
    var Aadhar:String= "",
    var Pan:String= "",
    var Name:String= "",

){
    fun User(){}
    constructor() : this("","",
        "",
        "", ""
    )
}
