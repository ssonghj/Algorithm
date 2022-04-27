import java.util.*;
class Solution {
    
    static int answer = 0;

    public int solution(int[] numbers, int target) {
        
        dfs(numbers[0],numbers,target,1);
        dfs(-numbers[0],numbers,target,1);
        
        return answer;
    }
    
    public static void dfs(int N, int[] numbers, int t, int cnt){
        if(cnt == numbers.length && N == t){
            // System.out.println(answer);
            
            answer++;
            return;
        }
        if(cnt>= numbers.length){
            return;
        }
        
        dfs(N+numbers[cnt], numbers,t,cnt+1);
        dfs(N-numbers[cnt],numbers,t,cnt+1);
    }
}