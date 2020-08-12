package com.tfcporciuncula.phonemoji

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.tfcporciuncula.phonemoji.internal.PhoneNumberUtilInstanceProvider
import com.tfcporciuncula.phonemoji.internal.TextDrawable

class PhonemojiTextInputLayout : TextInputLayout {

  private val phoneNumberUtil = PhoneNumberUtilInstanceProvider.get(context)

  private var showFlag = true
  var flagSize = 0f

  constructor(context: Context) : super(context) {
    init(null)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    init(attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(attrs)
  }

  private fun init(attrs: AttributeSet?) {
    attrs?.let {
      with(context.theme.obtainStyledAttributes(attrs, R.styleable.PhonemojiTextInputLayout, 0, 0)) {
        try {
          showFlag = getBoolean(R.styleable.PhonemojiTextInputLayout_phonemoji_showFlag, true)
          val flagSizeFromAttr = getDimension(R.styleable.PhonemojiTextInputLayout_phonemoji_flagSize, 0f)
          flagSize = flagSizeFromAttr.takeIf { it > 0 } ?: resources.getDimension(R.dimen.phonemoji_default_flag_size)
        } finally {
          recycle()
        }
      }
    }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (showFlag) watchPhoneNumber()
  }

  private fun watchPhoneNumber() {
    val phonemojiEditText = editText as? PhonemojiTextInputEditText
    checkNotNull(phonemojiEditText) { "PhonemojiTextInputLayout requires a PhonemojiTextInputEditText child" }
    PhonemojiHelper.watchPhoneNumber(phoneNumberUtil, phonemojiEditText) {
      startIconDrawable = TextDrawable(it, flagSize)
    }
  }
}
