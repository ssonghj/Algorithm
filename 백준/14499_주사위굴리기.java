import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    static int N, M, dir, x, y;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        StringBuffer sb = new StringBuffer();
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < K; i++) {
            dir = Integer.parseInt(st.nextToken()) - 1;
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            // 지도 바깥으로 이동 불가능 -> 바깥으로 이동시 해당 명령 무시
            if (check(nx, ny)) {
                continue;
            }

            // 갈 수 있으면 수행
            x = nx;
            y = ny;
            int res = moveDice();

            sb.append(res + "\n");
        }
        System.out.println(sb.toString());
    }

    static int[] dx = { 0, 0, -1, 1 };// 동서북남
    static int[] dy = { 1, -1, 0, 0 };

    public static int moveDice() {
        // 방향대로 주사위 굴리고 주사위 숫자 변경
        changeNumOfDice();

        // 굴린 주사위 수와 바닥 면 수 비교

        // 이동한 칸의 수가 0 이면 주사위 바닥면의수가 복사
        if (map[x][y] == 0) {
            map[x][y] = dice[3];
        }
        // 0이 아니면 칸의 수가 주사위 바닥면에 복사, 칸의 수는 0
        else {
            dice[3] = map[x][y];
            map[x][y] = 0;
        }
        return dice[0];
    }

    static int[] dice = { 0, 0, 0, 0, 0, 0 };// 0:윗면,1:바닥북쪽,2:바닥왼쪽,3:바닥,4:바닥오른쪽,5:바닥아래쪽

    public static void changeNumOfDice() {
        int[] newDice = new int[6];;

        // 동
        if (dir == 0) {
            newDice[4] = dice[0];
            newDice[1] = dice[1];
            newDice[0] = dice[2];
            newDice[2] = dice[3];
            newDice[3] = dice[4];
            newDice[5] = dice[5];//2 1 3 4 0 5
        }
        // 서
        else if (dir == 1) {
            newDice[2] = dice[0];
            newDice[1] = dice[1];
            newDice[3] = dice[2];
            newDice[4] = dice[3];
            newDice[0] = dice[4];
            newDice[5] = dice[5];
        }
        // 북
        else if (dir == 2) {
            newDice[1] = dice[0];
            newDice[3] = dice[1];
            newDice[2] = dice[2];
            newDice[5] = dice[3];
            newDice[4] = dice[4];
            newDice[0] = dice[5];
        }
        // 남
        else {
            newDice[5] = dice[0];
            newDice[0] = dice[1];
            newDice[2] = dice[2];
            newDice[1] = dice[3];
            newDice[4] = dice[4];
            newDice[3] = dice[5];
        }

        dice = newDice;
    }

    public static boolean check(int nx, int ny) {
        if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
            return true;
        }
        return false;
    }
}
