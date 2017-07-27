package  com.lordofprograms.notelin.cv

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText

/**
 * Created by Михаил on 08.07.2017.
 */
class MyEditText : EditText {

    var cxt: Context
    var b = false
    lateinit var tw: TextWatcher

    constructor(context: Context): super(context){
        this.cxt = context
        setListener()
    }

    constructor(context: Context, atrset: AttributeSet): super(context, atrset){
        this.cxt = context
        setListener()
    }

    constructor(context: Context, atrset: AttributeSet, i: Int): super(context, atrset, i){
        this.cxt = context
        setListener()
    }

    fun setListener(){
        setOnTouchListener(TouchHelper(this))
    }

    fun setReadOnly(state: Boolean): Boolean{
        if(b != state){
            b = state
            isCursorVisible = !state
            val obj = text.toString()
            if(state){
                tw = TextWatcherHelper(this, obj)
                addTextChangedListener(tw)
            }
            removeTextChangedListener(tw)
        }
        return state
    }

}