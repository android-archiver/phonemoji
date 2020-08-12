package com.tfcporciuncula.phonemoji.internal

import android.content.Context
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

internal object PhoneNumberUtilInstanceProvider {

  private var phoneNumberUtil: PhoneNumberUtil? = null

  fun set(phoneNumberUtil: PhoneNumberUtil) {
    PhoneNumberUtilInstanceProvider.phoneNumberUtil = phoneNumberUtil
  }

  fun get(context: Context): PhoneNumberUtil =
    phoneNumberUtil ?: synchronized(this) { PhoneNumberUtil.createInstance(context) }
}
