package com.example.menuhomework.model.exceptions

class CityDoesNotExistException: Throwable(){
    override val message: String
        get() = "This city does not exist"
}