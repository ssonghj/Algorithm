import java.util.*;

class Solution {

    //시간->분단위
    public int timeToInt(String time) {
        String tmp[] = time.split(":");
        return Integer.parseInt(tmp[0]) * 60 + Integer.parseInt(tmp[1]);
    }

    public int[] solution(int[] fees, String[] records) {
        //키값으로 자동 정렬 가능 
        TreeMap<String, Integer> map = new TreeMap<>();

        for (String record : records) {
            //시간, 차량번호, 내역 
            String temp[] = record.split(" ");
            //차량입차면 -1, 출차면 1 저장 
            int time = temp[2].equals("IN") ? -1 : 1;
            //입차, 출차에 따라 시간계산해서 곱해주기 (부호 다르기 때문에 빼고 더하기 가능)
            time *= timeToInt(temp[0]);
            //treemap에 차량번호, 맵비었을 경우 0넣고 아닐 경우 +time
            map.put(temp[1], map.getOrDefault(temp[1], 0) + time);
        }

        //저장위한 idx, 정답 저장 배열 
        int idx = 0, ans[] = new int[map.size()];
        //map값 가져오기 
        for (int time : map.values()) {
            //시간이 1보다 작다면 
            if (time < 1)
                time += 1439;// 1분미만

            time -= fees[0];// 기본 시간 빼기
            int cost = fees[1];//기본값 넣기 
            //시간 남았을 경우 
            if (time > 0) {
                //요금 계산 
                //0으로 나누어 떨어지지 않으면 +1해서 요금 세기
                cost += (time % fees[2] == 0 ? time / fees[2] : time / fees[2] + 1 * fees[3]);
            }

            //배열에 넣고 값 index증가 
            ans[idx++] = cost;
        }
        return ans;
    }
}