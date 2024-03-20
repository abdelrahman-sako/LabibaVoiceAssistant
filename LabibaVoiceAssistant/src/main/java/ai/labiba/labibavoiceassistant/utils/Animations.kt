package ai.labiba.labibavoiceassistant.utils

import ai.labiba.labibavoiceassistant.utils.ext.toPx
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator

object Animations {

    fun animateView(view: View, animation: Int) {
        val viewAnimation = AnimationUtils.loadLayoutAnimation(view.context, animation)


        view.startAnimation(viewAnimation.animation)
    }

    fun startFallDownAnimation(view: View, scalePercentage: Int) {


        val translateValueAnimator = ValueAnimator.ofFloat(-(20.toPx).toFloat(), 0f).apply {
            addUpdateListener {
                val value = it.animatedValue as Float
                view.translationY = value
            }

            interpolator = DecelerateInterpolator()
        }

        val alphaValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                val value = it.animatedValue as Float
                view.alpha = value
            }
            interpolator = DecelerateInterpolator()
        }

        val scaleValueAnimator = ValueAnimator.ofInt(scalePercentage, 100).apply {
            addUpdateListener {
                //percentage
                val value = it.animatedValue as Int

                view.scaleX *= (value / 100).toFloat()
                view.scaleY *= (value / 100).toFloat()
                view.pivotX *= 0.5f
                view.pivotY *= 0.5f
            }

            interpolator = DecelerateInterpolator()

        }


        val set = AnimatorSet()
        set.playTogether(translateValueAnimator, alphaValueAnimator, scaleValueAnimator)
        set.duration = 500
        set.startDelay = 500 * (15 / 100)
        set.start()
    }


    fun startScaleDownAnimation(view: View, scalePercentage: Int) {

        val scaleValueAnimator = ValueAnimator.ofInt(scalePercentage, 100).apply {
            addUpdateListener {
                //percentage
                val value = it.animatedValue as Int

                view.scaleX *= (value / 100).toFloat()
                view.scaleY *= (value / 100).toFloat()
                view.pivotX = 0f
                view.pivotY = 0f
            }


            duration = 500
            startDelay = 500 * (15 / 100)

            interpolator = DecelerateInterpolator()

        }

        scaleValueAnimator.start()


    }

}