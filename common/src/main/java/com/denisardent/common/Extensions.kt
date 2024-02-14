package com.denisardent.common

fun String.translateTagToEnglish(): String{
    return when(this){
        "Смотреть все" -> "All"
        "Лицо" -> "face"
        "Тело" -> "body"
        "Загар" -> "suntan"
        "Маски" -> "mask"
        else -> "Not a tag"
    }
}

fun CharSequence.validateField(): Boolean{
    if (this.contains(" ") || this.isBlank()){
        return false
    }
    for (i in this){
        if (!i.isCyrillic()){
            return false
        }
    }
    return true
}

fun Char.isCyrillic(): Boolean {
    if (Character.UnicodeBlock.of(this) ===  Character.UnicodeBlock.CYRILLIC){
        return true
    } else {
        return false
    }

}