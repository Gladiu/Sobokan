package com.projekt.sobokan

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.navigation.Navigation

class SelectLevel : Fragment() {

    lateinit var levelList: MutableList<String>
    var pickedLevel:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_select_level, container, false)
        val buttonLayout: LinearLayout = view.findViewById(R.id.buttonLayout)
        val buttonsToCreate:Int = GetLevelCount()

        val buttonStyle = androidx.appcompat.R.attr.buttonBarButtonStyle
        for (i in 1..buttonsToCreate){
            val newButton: Button = Button( ContextThemeWrapper(context, buttonStyle), null, buttonStyle) // Warning but it works

            newButton.text = i.toString()
            newButton.setOnClickListener {
                pickedLevel = i
                val bundle = bundleOf("pickedLevel" to pickedLevel)

                Navigation.findNavController(view).navigate(R.id.action_selectLevel_to_gameFragment, bundle)
            }
            buttonLayout.addView(newButton)

        }
        return view
    }

    fun GetLevelCount() : Int{
        var levelCount: Int = 1
        try{
            while(true) { // This loop will stop when we run out of files to iterate over
                requireActivity().application.assets.open(levelCount.toString()+".txt")
                levelCount++
            }
        }
        catch (e: Exception){
            //println(e.toString()) // This is wanted exception
            levelCount--
        }
        return levelCount
    }
}
