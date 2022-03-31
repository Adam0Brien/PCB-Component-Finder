package adam0brien.pcbhelper;

import java.util.ArrayList;
import java.util.LinkedList;

public class Utilities {



    //took this method from Stack overflow
    public static int diffValues(int[] numArray){  //https://stackoverflow.com/questions/32444193/count-different-values-in-array-in-java
        int numOfDifferentVals = 0;

        ArrayList<Integer> diffNum = new ArrayList<>();

        for(int i=0; i<numArray.length; i++){
            if(!diffNum.contains(numArray[i])){
                diffNum.add(numArray[i]);
            }
        }

        if(diffNum.size()==1){
            numOfDifferentVals = 0;
        }
        else{
            numOfDifferentVals = diffNum.size();
        }

        return numOfDifferentVals;
    }

}
