package com.example.doseloop.components

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.core.view.updatePadding
import com.example.doseloop.DoseLoopApplication
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
 *      cardStyle: Sets the style of the card, i.e. color and position of the left or right corner radius
 *
 * DrawerExampleFragment has multiple examples on how to utilize the DrawerMenu component and also on how to add onClick functionality to the buttons.
 */

const val OFFSET_DIVIDER = 4
const val ANIMATION_SPEED = 500L
const val DRAWER_AMOUNT = 4

@SuppressLint("ClickableViewAccessibility", "ResourceType", "InternalInsetResource")
class DrawerMenu @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, defStyleRes: Int = 0):
    LinearLayout (context, attrs, defStyle, defStyleRes), OnGestureListener {

    private var _binding: DrawerMenuBinding? = null
    val binding get() = _binding!!

    private var menuCardView: CardView
    private var menuImageView: ImageView
    private var menuButtonView: ImageButton
    private var menuTextView: TextView

    private var menuLayoutHidden: RelativeLayout
    private var menuLayoutVisible: RelativeLayout
    private var scrolling = false

    private val gestureDetector: GestureDetector = GestureDetector(this)

    var visible = false

    private val screenWidth = context.resources.displayMetrics.widthPixels
    private val screenHeight = context.resources.displayMetrics.heightPixels
    private val offset = screenWidth - (screenWidth / OFFSET_DIVIDER)
    private var chosenOffSet : Float = 0f
    private var curPos = 0f
    private var bottomMargin = 0

    private val leftOffset = -offset.toFloat()
    private val rightOffset = offset.toFloat()

    // Layout's default values set everything correctly if drawer is on the left side of the screen. If it's on the right side, we just swap the places of some elements

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

    private var cardStyle: String = "0"

    init {
        _binding = DrawerMenuBinding.inflate(LayoutInflater.from(context),this, true)

        menuCardView = findViewById(R.id.drawer_menu_card)
        menuButtonView = findViewById(R.id.drawer_menu_button)
        menuImageView = findViewById(R.id.drawer_menu_image)
        menuTextView = findViewById(R.id.drawer_menu_text)

        menuLayoutVisible = findViewById(R.id.drawer_menu_visible)
        menuLayoutHidden = findViewById(R.id.drawer_menu_hidden)

        val gestureListener = OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && scrolling) handleScrollEnded()
            gestureDetector.onTouchEvent(event)
        }
        this.setOnTouchListener(gestureListener)

        attrs?.let {

            val typedArray = context.obtainStyledAttributes(it, R.styleable.DrawerMenu)
            menuSide = typedArray.getString(R.styleable.DrawerMenu_menuSide) ?: "left"
            cardStyle = typedArray.getString(R.styleable.DrawerMenu_cardStyle) ?: "0"

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

            val resourceId: Int =
                DoseLoopApplication.instance.resources.getIdentifier("status_bar_height", "dimen", "android")
            bottomMargin = resources.getDimension(resourceId).toInt()

            this.updatePadding(right = if (menuSide == "left") 50 else 0, left = if (menuSide == "left") 0 else 50)
            menuCardView.setBackgroundResource(if (menuSide == "left") R.drawable.left_card else R.drawable.right_card)
            menuCardView.background.level = cardStyle.toInt()
            menuCardView.layoutParams.height = ((screenHeight - bottomMargin) / DRAWER_AMOUNT)

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

        /* We utilize the ObjectAnimator to move the DrawerMenu outside of the screen before rendering.
         * We then add an onClickListener to use ObjectAnimator to move the menu back and forth. */

        chosenOffSet = if (menuSide == "left") leftOffset else rightOffset
        curPos = chosenOffSet
        moveDrawer(chosenOffSet)

        val iconGestureListener = OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                moveDrawer(if (visible) chosenOffSet else 0f, ANIMATION_SPEED)
                visible = !visible
                if (scrolling) handleScrollEnded()
            }
            gestureDetector.onTouchEvent(event)
        }
        menuLayoutVisible.setOnTouchListener(iconGestureListener)

    }

    override fun onShowPress(p0: MotionEvent?) {}
    override fun onLongPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }
    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }
    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        scrolling = true

        val target = curPos.minus(p0?.x?.minus(p1?.x ?: 0f) ?: 0f)
        val drawRange = if (menuSide == "left") chosenOffSet..0f
        else 0f..chosenOffSet

        if (target in drawRange) {
            moveDrawer(target)
        }
        return true
    }

    private fun handleScrollEnded() {

        val visibleTolerance = screenWidth / 5
        val hiddenTolerance = screenWidth / 1.5

        if (visible) {
            visible = if ((curPos < -visibleTolerance && menuSide == "left") || (curPos > visibleTolerance && menuSide == "right")) {
                moveDrawer(chosenOffSet, ANIMATION_SPEED)
                false
            } else {
                moveDrawer(0f, ANIMATION_SPEED)
                true
            }
        } else {
            visible = if ((curPos > -hiddenTolerance && menuSide == "left") || (curPos < hiddenTolerance && menuSide == "right")) {
                moveDrawer(0f, ANIMATION_SPEED)
                true
            } else {
                moveDrawer(chosenOffSet, ANIMATION_SPEED)
                false
            }
        }
        scrolling = false
    }

    private fun moveDrawer(target: Float = 0f, duration: Long = 0) {
        ObjectAnimator.ofFloat(this, "translationX", target).apply {
            this.duration = duration
            start()
            curPos = target
        }
    }
}