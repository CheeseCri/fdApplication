package customadapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportgameprototype.CheckBoxData
import com.example.sportgameprototype.PlayerDetailActvity
import com.example.sportgameprototype.PlayerInfo
import com.example.sportgameprototype.R


class LotteryAdapter(private val context : Context,
                     private val lotteryPlayerList : ArrayList<PlayerInfo>,
                     private val rtmCheckBoxList : ArrayList<CheckBoxData>,
                     private val ldCheckBoxList : ArrayList<CheckBoxData>)
    : RecyclerView.Adapter<LotteryAdapter.LotteryViewHolder>(){
    var rtmCount : Int = 0
    var LdCount : Int = 0
    lateinit var rtmPid : String
    lateinit var ldPid : String

//    interface OnItemClickListener {
//        fun onItemClick(view : View, position : Int)
//    }
//
//    fun setOnItemClickListener(listener : OnItemClickListener){
//
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LotteryViewHolder {
        return LotteryViewHolder(LayoutInflater.from(context).inflate(
                R.layout.lottery_player_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: LotteryViewHolder, position: Int) {
        // 아이템 클릭 리스너 할
        // 텍스트 뷰 바인딩
        holder.tvLotteryItemPlayerName.text = lotteryPlayerList[position].playerName
        holder.tvLotteryItemPlayerNum.text = lotteryPlayerList[position].backNumber.toString()
        // No.7 Issue
        val cbListener = CompoundButton.OnCheckedChangeListener {  buttonView, isChecked ->
            when (buttonView.id) {
                R.id.cb_best_rtm -> {
                    Log.d(
                            "USERLOG-LOTTERY-CB",
                            "RUNNING TIME selected \n" +
                                    "P_ID : ${lotteryPlayerList[position].playerId}, " +
                                    "P_NAME : ${lotteryPlayerList[position].playerName}\n"
                    )
                    rtmCheckBoxList[position].checked = isChecked
                }
                R.id.cb_best_ld -> {
                    Log.d(
                            "USERLOG-LOTTERY-CB",
                            "LEAD DISTANCE selected \n" +
                                    "P_ID : ${lotteryPlayerList[position].playerId}, " +
                                    "P_NAME : ${lotteryPlayerList[position].playerName}"
                    )
                    ldCheckBoxList[position].checked = isChecked
                }
            }
        }

        holder.cbBestLd.setOnCheckedChangeListener(cbListener)
        holder.cbBestRtm.setOnCheckedChangeListener(cbListener)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, PlayerDetailActvity::class.java)
            intent.putExtra("playerID", lotteryPlayerList[position].playerId)
            it.context.startActivity(intent)
        }

        // 체크박스 리스트 업데이트
        if (position >= rtmCheckBoxList.size) {
            Log.d("USERLOG-LOTTERY-CB",": rtmCheckBoxList size : ${rtmCheckBoxList.size}")
            rtmCheckBoxList.add(position, CheckBoxData(lotteryPlayerList[position].playerId, false))
            ldCheckBoxList.add(position, CheckBoxData(lotteryPlayerList[position].playerId, false))
        }

        Log.d("USERLOG-LOTTERY-CB",": rtmCheckBoxList[$position] : ${rtmCheckBoxList[position].checked}")
        holder.cbBestLd.isChecked = ldCheckBoxList[position].checked
        holder.cbBestRtm.isChecked = rtmCheckBoxList[position].checked
    }

    override fun getItemCount(): Int {
        return lotteryPlayerList.size
    }

    class LotteryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbBestLd: CheckBox = view.findViewById(R.id.cb_best_ld)
        val cbBestRtm: CheckBox = view.findViewById(R.id.cb_best_rtm)
        val tvLotteryItemPlayerName: TextView = view.findViewById(R.id.tv_lottery_item_player_name)
        val tvLotteryItemPlayerNum: TextView = view.findViewById(R.id.tv_lottery_item_player_number)
    }



}


