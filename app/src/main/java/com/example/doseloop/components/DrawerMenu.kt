package com.example.doseloop.components

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import com.example.doseloop.R
import com.example.doseloop.databinding.DrawerMenuBinding

/**
 * Custom component for the DrawerMenu. Has the following attributes:
 *
 *      menuCardColorRef: Sets the background color of the DrawerMenu. Expects a color reference, e.g. "@color/white"
 *      menuText: Sets the text of the DrawerMenu. Expects a string id from resources, e.g. "@string/lorem"
 *      menuTextColorRef: Sets the color of the DrawerMenu text. Expects a color reference, e.g. "@color/white"
 *      menuImageRef: Sets the image source of the ImageView that's on the side of the drawer. Expects a drawable reference, e.g. "@drawable/ic_send"
 *      menuImageColorRef: Sets the color of the ImageView that's on the side of the drawer. Expects a color reference, e.g. "@color/white"
 *      menuButtonRef: Sets the image source of the ImageButton on the drawer menu. Expects a drawable reference, e.g. "@drawable/ic_send"
 *      menuButtonColorRef: Sets the color of the ImageButton on the drawer menu. Expects a color reference, e.g. "@color/white"
 *      menuSide: Sets the screen side of the drawer. Expects a string of either "left" or "right". Defaults to "left".
 *
 * DrawerExampleFragment has multiple examples on how to utilize the DrawerMenu component and also on how to add onClick functionality to the buttons.
 */

const val VALUE_LEFT = -800F
const val VALUE_RIGHT = 800F
const val ANIMATION_SPEED = 500L

class DrawerMenu @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0): LinearLayout (context, attrs, defStyle, defStyleRes) {

    private var _binding: DrawerMenuBinding? = null
    val binding get() = _binding!!

    private var menuCardView: CardView
    private var menuImageView: ImageView
    private var menuButtonView: ImageButton
    private var menuTextView: TextView

    private var menuLayoutHidden: RelativeLayout
    private var menuLayoutVisible: RelativeLayout

    var visible = false

    private var menuSide: String = "left"
        set(value) {
            field = value
            if (value == "right") {
                var lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                lp.addRule(RelativeLayout.RIGHT_OF, menuLayoutVisible.id)
                menuLayoutHidden.layoutParams = lp
                lp = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                lp.addRule(RelativeLayout.CENTER_VERTICAL, 1)
                lp.addRule(RelativeLayout.ALIGN_PARENT_START, 1)
                menuLayoutVisible.layoutParams = lp
            }
        }

    private var menuCardColor: Int = R.color.light_green
        set(value) {
            field = value
            menuCardView.setCardBackgroundColor(value)
        }

    private var menuText: String = ""
        set(value) {
            field = value
            menuTextView.text = value
        }

    private var menuTextColor: Int = R.color.black
        set(value) {
            field = value
            menuTextView.setTextColor(value)
        }

    private var menuImage: Drawable? = null
        set(value) {
            field = value
            value?.let {
                menuImageView.setImageDrawable(it)
            }
        }

    private var menuImageColor: Int = R.color.black
        set(value) {
            field = value
            menuImageView.setColorFilter(value)
        }

    private var menuButtonImage: Drawable? = null
        set(value) {
            field = value
            value?.let {
                menuButtonView.setImageDrawable(it)
            }
        }

    private var menuButtonImageColor: Int = R.color.black
        set(value) {
            field = value
            menuButtonView.setColorFilter(value)
        }

    init {
        _binding = DrawerMenuBinding.inflate(LayoutInflater.from(context),this, true)

        menuCardView = findViewById(R.id.drawer_menu_card)
        menuButtonView = findViewById(R.id.drawer_menu_button)
        menuImageView = findViewById(R.id.drawer_menu_image)
        menuTextView = findViewById(R.id.drawer_menu_text)

        menuLayoutVisible = findViewById(R.id.drawer_menu_visible)
        menuLayoutHidden = findViewById(R.id.drawer_menu_hidden)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.DrawerMenu)
            val side = typedArray.getString(R.styleable.DrawerMenu_menuSide) ?: "left"
            // Side can be null
            menuSide = if (side == null) "left" else side.toString()
            Log.d("SIDE", menuSide)

            menuText = resources.getText(
                typedArray.getResourceId(
                    R.styleable.DrawerMenu_menuText,
                    R.string.lorem
                )
            ).toString()
            menuTextColor = resources.getColor(
                typedArray.getResourceId(
                    R.styleable.DrawerMenu_menuTextColorRef,
                    R.color.black
                )
            )
            menuCardColor = resources.getColor(
                typedArray.getResourceId(
                    R.styleable.DrawerMenu_menuCardColorRef,
                    R.color.light_green
                )
            )
            val imageRes = typedArray.getResourceId(R.styleable.DrawerMenu_menuImageRef, -1)
            if (imageRes != -1) {
                menuImage = AppCompatResources.getDrawable(context, imageRes)
            }
            menuImageColor = resources.getColor(
                typedArray.getResourceId(
                    R.styleable.DrawerMenu_menuImageColorRef,
                    R.color.black
                )
            )
            val buttonRes = typedArray.getResourceId(R.styleable.DrawerMenu_menuButtonRef, -1)
            if (buttonRes != -1) {
                menuButtonImage = AppCompatResources.getDrawable(context, buttonRes)
            }
            menuButtonImageColor = resources.getColor(
                typedArray.getResourceId(
                    R.styleable.DrawerMenu_menuButtonColorRef,
                    R.color.black
                )
            )

            typedArray.recycle()
        }

        /**
         * Here we utilize the ObjectAnimator to move the DrawerMenu outside of the screen before rendering.
         * We then add an onClickListener to use ObjectAnimator to move the menu back and forth.
         */

        val offSet = if (menuSide == "left") VALUE_LEFT else VALUE_RIGHT

        ObjectAnimator.ofFloat(this, "translationX", offSet).apply {
            duration = 1
            start()
        }

        this.setOnClickListener {
            if (visible) {
                ObjectAnimator.ofFloat(it, "translationX", offSet).apply {
                    duration = ANIMATION_SPEED
                    start()
                }
            } else {
                ObjectAnimator.ofFloat(it, "translationX", 0F).apply {
                    duration = ANIMATION_SPEED
                    start()
                }
            }
            visible = !visible
        }
    }
}