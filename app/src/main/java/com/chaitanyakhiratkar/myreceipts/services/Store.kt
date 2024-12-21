package com.chaitanyakhiratkar.myreceipts.services

class Store {
    lateinit var store:StoreModel

    fun saveUser(newStore:StoreModel){
        store = newStore;
    }

    fun getStoreDetails():StoreModel{
        return store
    }


    fun getStorePhoneNumber(): String? {
        return store.phoneNumber
    }

}

val storeApi = UserService()
