package  com.lordofprograms.notelin.cv

import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Михаил on 08.07.2017.
 */
class TouchHelper(myEditText: MyEditText) : View.OnTouchListener {

    var met : MyEditText = myEditText

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if(met.b){
            this.met.onTouchEvent(event)
            val inputMethodManager = view.context.getSystemService("input_method") as InputMethodManager

            if(true){
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            else{
                this.met.onTouchEvent(event)
            }
        }
        return true
    }


}