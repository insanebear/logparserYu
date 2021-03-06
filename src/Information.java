import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Youlim Jung on 2017-05-04.
 */
public class Information {

    public String subtractTimes(String end, String start){
        int[] startTime = strToInt(start);
        int[] endTime = strToInt(end);
        int[] res = new int[startTime.length];

        for(int i=0; i<startTime.length; i++) {
            res[i] = endTime[i] - startTime[i];
            if(res[i] < 0)
            {
                res = subtractHandle(res, i); // start from "hours" unit
            }
        }
        return intToStr(res);
    }

    public int[] subtractHandle(int[] resArr, int pos){
        if(pos-1 >= 0){ // index is not under 0
            resArr[pos-1] -= 1;
            if (pos != 3) { // h, m, s
                resArr[pos] = 60 + resArr[pos];
            } else { // ms
                resArr[pos] = 1000 + resArr[pos];
            }
            if(resArr[pos-1] < 0){
                subtractHandle(resArr, pos-1);
            }
        }
        return resArr;
    }

    public String addTimes(String sumDuration, String newDuration){
        int[] sum = strToInt(sumDuration);
        int[] operand = strToInt(newDuration);
        int[] res = new int[4];

        for(int i=0; i<4; i++) {
            res[i] = sum[i] + operand[i];
            if((res[i] >= 60 && i<=2) || (res[i] >= 1000 && i==3)){
                res = addHandle(res, i);
            }
        }
        return intToStr(res);
    }

    public int[] addHandle(int[] resArr, int pos){
        if(pos-1 >= 0){ // index is not under 0
            resArr[pos-1] += 1;
            if (pos != 3) { // h, m, s
                resArr[pos] = resArr[pos] - 60;
            } else { // ms
                resArr[pos] = resArr[pos] - 1000;
            }
            if((resArr[pos-1] >= 60 && (pos-1)<=2) || (resArr[pos-1] >= 1000 && (pos-1)==3)){
                addHandle(resArr, pos-1);
            }
        }
        return resArr;
    }

    private int[] strToInt(String time){
        String[] s = time.split(" ");
        int[] res = new int[s.length];

        // strip char
        s[0] = s[0].substring(0, s[0].length()-1);
        s[1] = s[1].substring(0, s[1].length()-1);
        s[2] = s[2].substring(0, s[2].length()-1);
        s[3] = s[3].substring(0, s[3].length()-2);

        for (int i=0; i<s.length; i++) {
            res[i] = Integer.parseInt(s[i]);
        }
        return res;
    }

    private String intToStr(int[] time){
        String[] strings = new String[time.length];

        for(int i=0; i<4; i++){
            strings[i] = String.valueOf(time[i]);
        }
//        strings[0] = String.valueOf(time[0]);
//        strings[1] = String.valueOf(time[1]);
//        strings[2] = String.valueOf(time[2]);
//        strings[3] = String.valueOf(time[3]);

        return padZero(strings);
    }

    public String padZero(String[] strings){
        for (int i=0; i<strings.length; i++) {
            if(strings[i]==null && i != 3){ // no digit h, m, s
                strings[i] = "00";
            }else if(strings[i].length()==1 && i != 3){ // 1 digit h, m, s
                strings[i] = "0"+strings[i];
            }else if(i == 3){ // ms
                if(strings[i].length() == 1){
                    strings[i] = "00"+strings[i];
                }else if (strings[i].length() == 2){
                    strings[i] = "0"+strings[i];
                }else if(strings[i]==null){
                    strings[i] = "000";
                }
            }
        }
        return addUnits(strings);
    }

    private String addUnits(String[] values){
        return values[0]+"h "+values[1]+"m "+values[2]+"s "+values[3]+"ms";
    }

    public void setPrevEndTime(String time){}

    public String getPrevEndTime(){ return ""; }

    public String getPrevStartTime(){ return ""; }

    public int getInfoArrLength(){ return 0; }

    public void setDuration(String duration) { }

    public void addInfoArr(String[] infoArr){ }

    public void printNamedTrace(BufferedWriter out, ArrayList<String[]> trace) throws IOException {
        int idx = 1;
        for (String[] s : trace) {
            System.out.println("["+idx+"th trace]");
            System.out.print("Start time: "+s[1]+" ");
            System.out.print("End time: "+s[2]+" ");
            System.out.println("Duration: "+s[3]);

            out.write("["+idx+"th trace]");out.newLine();
            out.write("Start time: "+s[1]+" End time: "+s[2]+" Duration: "+s[3]);out.newLine();

            idx++;
        }
    }

    public void printTrace(BufferedWriter out,ArrayList<String[]> trace) throws IOException {
        int idx = 1;
        for (String[] s : trace) {
            String tmpStr = "";

            tmpStr = "["+idx+"th trace]";
            System.out.println(tmpStr);
            out.write(tmpStr); out.newLine();

            tmpStr="Start time: "+s[0]+" End time: "+s[1]+" Duration: "+s[2];
            System.out.println(tmpStr);
            out.write(tmpStr); out.newLine();

            idx++;
        }
        System.out.println();
    }

}
