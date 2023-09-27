package com.padcmyanmar.abbc.wechatredesign.data

object DummyData{
    fun getFirstLetterList(): List<Char> {
        val alphabet = ('A'..'Z').toList()
        return alphabet
    }
}