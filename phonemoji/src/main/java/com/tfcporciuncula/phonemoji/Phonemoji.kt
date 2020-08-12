package com.tfcporciuncula.phonemoji

import com.tfcporciuncula.phonemoji.internal.PhoneNumberUtilInstanceProvider
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

object Phonemoji {

  /**
   * Stores the given [PhoneNumberUtil] instance in memory to be used by the library.
   *
   * @param phoneNumberUtil A [PhoneNumberUtil] instance.
   */
  @JvmStatic fun setPhoneNumberUtilInstance(phoneNumberUtil: PhoneNumberUtil) =
    PhoneNumberUtilInstanceProvider.set(phoneNumberUtil)
}
