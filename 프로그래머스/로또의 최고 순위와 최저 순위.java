import java.util.*;

class Solution {

    public int[] solution(int[] lottos, int[] win_nums) {
        //이기는 번호 해시셋에 넣기 
        HashSet<Integer> wn = new HashSet<>();
        for(int n : win_nums){
            wn.add(n);
        }

        //자기 로또 번호 중 0 제외하고 당첨번호랑 몇 개 맞는지 확인 
        int zeroNum = 0;
        int collectTmp = 0;
        for(int myNum : lottos){
            if(myNum == 0){
                zeroNum++;
            }
            //로또 번호 일치 시 
            if(wn.contains(myNum)){
                collectTmp++;
            }
        }
        
        //0개수 가지고 최고, 최저 등수 확인
        int lowCnt = collectTmp;
        int highCnt = collectTmp + zeroNum;
        
        int[] answer = new int[2];
        //최고 등수, 최저 등수 계산 후 넣기 
        answer[0] = calculate(highCnt);
        answer[1] = calculate(lowCnt);
        
        return answer;
    }
    public static int calculate(int tmp){
        switch(tmp){
            case 6: return 1;
            case 5: return 2;
            case 4: return 3;
            case 3: return 4;
            case 2: return 5;
            default: return 6;
        }
    }
}