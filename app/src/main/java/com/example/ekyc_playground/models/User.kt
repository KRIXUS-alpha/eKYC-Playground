package com.example.ekyc_playground.models

data class User(
    var Email:String = "",
    var Address:String= "",
    var Aadhar:String= "",
    var Pan:String= "",
    var Name:String= "",
    var ProfileP:String = "",
    var ID:String = "",
    var idDetails:String = "",

){
    fun User(){}
    constructor() : this("","",
        "",
        "", "","","",""
    )
}
