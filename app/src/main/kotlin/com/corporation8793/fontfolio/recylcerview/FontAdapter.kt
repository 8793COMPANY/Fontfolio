package com.corporation8793.fontfolio.recylcerview

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.FontInformation
import com.corporation8793.fontfolio.common.Fontfolio

import com.corporation8793.fontfolio.library.room.entity.font.Font


class FontAdapter(val context: Context, val activity:FragmentActivity?) : RecyclerView.Adapter<FontAdapter.ItemViewHolder>(){
    var datas = mutableListOf<Font>()
    var colors = arrayOf("#eaebde", "#ebc4ff", "#08000000", "#f8eddf","#F8EDDF","#EDF1FF","#F8DEDE","#DEEBE8","#0D000000")
    var strings = arrayOf(R.string.font_item_content,R.string.font_item_content2)
    var font_license_info = HashMap<String, String>()
    lateinit var license_info : List<String>
    lateinit var view : View

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ranking = itemView.findViewById<TextView>(R.id.ranking)
        val font_name = itemView.findViewById<TextView>(R.id.fontName)
        val copyright = itemView.findViewById<TextView>(R.id.copyright)
        val font_license = itemView.findViewById<TextView>(R.id.font_license)
        val font_license_background = itemView.findViewById<LinearLayout>(R.id.font_license_background)
//                val content = itemView.findViewById<TextView>(R.id.content)

        fun bind(font: Font){
            font_name.text = font.fontName.split("-")[0]
            copyright.text = font.fontCopyrightHolder

            Log.e("fontLicenseDescription",font.fontLicenseDescription)
            if (font.fontLicenseDescription.trim().equals("inumocca_type")){
                font_license.text = "inumocca_type"
                font_license_background.backgroundTintList = (ColorStateList.valueOf(Color.parseColor("#485e90")))
            }else{
                license_info = font_license_info.get(font.fontLicenseDescription)!!.split(",")
                font_license.text = license_info[0]
                font_license_background.backgroundTintList = (ColorStateList.valueOf(Color.parseColor(license_info[1])))
            }

            Log.e("fontname",font.fontName.toLowerCase().replace(" ", "_").replace("-","_")+".ttf")
            Fontfolio().changeFontOfTextView(activity,font_name,font.fontName)
        }


    }

    inner class ItemViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val font_name = itemView.findViewById<TextView>(R.id.fontName)
        val copyright = itemView.findViewById<TextView>(R.id.copyright)
        val font_license = itemView.findViewById<TextView>(R.id.font_license)
        val font_license_background = itemView.findViewById<LinearLayout>(R.id.font_license_background)
//                val content = itemView.findViewById<TextView>(R.id.content)

        fun bind(font: Font){
            font_name.text = font.fontName.split("-")[0]
            copyright.text = font.fontCopyrightHolder

            Log.e("fontLicenseDescription",font.fontLicenseDescription)
            if (font.fontLicenseDescription.trim().equals("inumocca_type")){
                font_license.text = "inumocca_type"
                font_license_background.backgroundTintList = (ColorStateList.valueOf(Color.parseColor("#485e90")))
            }else{
                license_info = font_license_info.get(font.fontLicenseDescription)!!.split(",")
                font_license.text = license_info[0]
                font_license_background.backgroundTintList = (ColorStateList.valueOf(Color.parseColor(license_info[1])))
            }

            Log.e("fontname",font.fontName.toLowerCase().replace(" ", "_").replace("-","_")+".ttf")
            Fontfolio().changeFontOfTextView(activity,font_name,font.fontName)
        }


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(
            R.layout.font_list_itemview,
            parent,
            false
        )
        font_license_info.put("OFL", "OFL,#2d717e")
        font_license_info.put("OFL (Open Font License)", "Open Font License,#2d717e")
        font_license_info.put("Paid Fonts", "Paid font,#c70000")
        font_license_info.put("Adobe License", "Paid font,#c70000")
        font_license_info.put("Free for Personal Use only", "Free for Personal use,#ffba00")
        font_license_info.put("100% Free", "100% FREE,#ffba00")
        font_license_info.put("Demo Font", "Demo Font,#485e90")
        font_license_info.put("GNU / GPL / OFL", "GNU / GPL / OFL,#485e90")
        font_license_info.put("UInkown", "UInkown,#485e90")
//        when (viewType) {
//            0 -> {
//                 view = LayoutInflater.from(parent.context).inflate(
//                    R.layout.font_list_itemview,
//                    parent,
//                    false
//                )
//            }
//            1 -> {
//                view = LayoutInflater.from(parent.context).inflate(
//                    R.layout.font_list_itemview2,
//                    parent,
//                    false
//                )
//
//            }
//        }
//      return if(viewType ==0) ItemViewHolder(view) else  ItemViewHolder2(view)
        return ItemViewHolder(view)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(datas[position])

        Log.e("in", "check")

        when(holder.itemViewType){
            0->{

            }
            1->{

            }
        }

        holder.itemView.setOnClickListener({
            Log.e("item", "click!")
            var intent :Intent = Intent(activity,FontInformation::class.java)
            intent.putExtra("fontName",datas[position].fontName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        })

    }

    override fun getItemCount(): Int = datas.size


}