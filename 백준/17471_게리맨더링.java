import java.io.*;
import java.util.*;

public class Solution {
    static int N, select;
    static int minDiff = Integer.MAX_VALUE;
    static int[] activate;
    static int[][] arr;
    static int[] population;

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        population = new int[N + 1];
        for (int i = 0; i < N; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }

        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                int end = Integer.parseInt(st.nextToken()) - 1;
                arr[i][end] = arr[end][i] = 1;
            }
        }

        // 조합통해 2구역으로 나눔 -> 겹치니까 이렇게만 해도 ㄱㅊ
        for (int i = 1; i <= N / 2; i++) {
            // 조합선택할 숫자만큼의 크기
            activate = new int[i];
            // 조합 선택할 숫자 크기
            select = i;
            // 조합 수행
            comb(0, 0);
        }

        // 구역나뉘지않음
        if (minDiff == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {// 구역 나뉨
            System.out.println(minDiff);
        }
    }

    public static void comb(int start, int selectCnt) {
        // 선택된 숫자일 시
        if (selectCnt == select) {
            calculate();
            return;
        }

        for (int i = start; i < N; i++) {
            activate[selectCnt] = i;// 자리 활성화
            // 들고 들어가기
            comb(i + 1, selectCnt + 1);
        }
    }

    public static void calculate() {
        // 구역 활성화된 곳 들어옴
        HashSet<Integer> region1 = new HashSet<>();
        HashSet<Integer> region2 = new HashSet<>();
        // 구역 1
        for (int i = 0; i < activate.length; i++) {
            region1.add(activate[i]);
        }
        // 구역 2
        for (int i = 0; i < N; i++) {
            if (!region1.contains(i)) {
                region2.add(i);
            }
        }

        // 각각 이어져있는지 확인 후
        boolean isLink1 = bfs(region1);
        boolean isLink2 = bfs(region2);
        // 이어져있다면 각 구역 인구 더하고 비교 , 한번이라도 구역 더해졌는지 확인후 check하기
        if (isLink1 && isLink2) {
            int p1 = 0;
            int p2 = 0;
            for (int r : region1) {
                p1 += population[r];
            }
            for (int r : region2) {
                p2 += population[r];
            }

            minDiff = Math.min(minDiff, Math.abs(p1 - p2));
        }
        // 안이어져있다면 패스
    }

    public static boolean bfs(HashSet<Integer> region) {
        int[] hsArr = new int[region.size()];
        int i = 0;
        for (Integer s : region) {
            hsArr[i++] = s;
            // System.out.println(hsArr[i - 1]);
        }

        Deque<Integer> dq = new LinkedList<>();
        boolean[] visited = new boolean[N];
        dq.add(hsArr[0]);
        visited[hsArr[0]] = true;

        while (!dq.isEmpty()) {
            int n = dq.poll();

            for (int k = 0; k < hsArr.length; k++) {
                int tmp = hsArr[k];
                // 연결되어있고 , 아직 방문 안했고, 같은 구역이면
                if (arr[tmp][n] == 1 && !visited[tmp] && inRegion(region, tmp)) {
                    dq.add(hsArr[k]);
                    visited[hsArr[k]] = true;
                }
            }
        }
        // 하나라도 연결 x경우
        for (int n : region) {
            if (!visited[n]) {
                return false;
            }
        }
        return true;
    }

    public static boolean inRegion(HashSet<Integer> hs, int tmp) {
        // hs안에 tmp가 있어야만 한다.
        if (hs.contains(tmp)) {
            return true;
        }
        return false;
    }
}