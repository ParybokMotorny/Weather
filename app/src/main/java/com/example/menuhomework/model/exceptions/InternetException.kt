package com.example.menuhomework.model.exceptions

class InternetException : Throwable(){
    override val message: String
        get() = "Check your internet connection"
}