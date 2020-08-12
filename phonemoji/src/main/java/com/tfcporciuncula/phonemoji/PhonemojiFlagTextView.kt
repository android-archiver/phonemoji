package com.tfcporciuncula.phonemoji

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatTextView
import com.tfcporciuncula.phonemoji.internal.PhoneNumberUtilInstanceProvider

/**
 * Displays an emoji flag based on the international phone number in an independent [PhonemojiTextInputEditText]
 * referenced by the `flagFor` attribute.
 */
class PhonemojiFlagTextView : AppCompatTextView {

  private val phoneNumberUtil = PhoneNumberUtilInstanceProvider.get(context)

  @IdRes private var editTextResId = -1

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    init(attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(attrs)
  }

  private fun init(attrs: AttributeSet?) {
    requireNotNull(attrs) { "AttributeSet must not be null and must contain the attribute flagFor." }

    with(context.theme.obtainStyledAttributes(attrs, R.styleable.PhonemojiFlagTextView, 0, 0)) {
      try {
        editTextResId = getResourceId(R.styleable.PhonemojiFlagTextView_phonemoji_flagFor, -1)
        check(editTextResId != -1) { "The attribute flagFor must be provided so we know what input to watch." }
      } finally {
        recycle()
      }
    }

    setTextColor(PhonemojiHelper.EMOJI_COLOR)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    watchPhoneNumber()
  }

  private fun watchPhoneNumber() {
    val editText = rootView.findViewById<View>(editTextResId) as? PhonemojiTextInputEditText
    checkNotNull(editText) { "View referenced in flagFor isn't a PhonemojiTextInputEditText" }
    PhonemojiHelper.watchPhoneNumber(phoneNumberUtil, editText) { text = it }
  }
}
