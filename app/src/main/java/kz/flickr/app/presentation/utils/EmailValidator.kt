package kz.flickr.app.presentation.utils

import kz.flickr.app.system.TextUtils

import java.util.regex.Pattern

object EmailValidator {

    private val EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && EMAIL_ADDRESS.matcher(target).matches()
    }

}
