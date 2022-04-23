import java.io.*;
import java.util.*;

public class Solution {
    static int N, M, K;
    static int[][] map;
    static int result;
    static int[] dice = { 2, 4, 6, 3, 5, 1 };
    static int[] dx = { 0, 1, 0, -1 };// 동남서북 -> 시계방향 회전
    static int[] dy = { 1, 0, -1, 0 };
    static int curX, curY, dir;

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        result = 0;
        curX = 0;
        curY = 0;
        dir = 0;

        // k만큼 주사위 굴리기
        for (int i = 0; i < K; i++) {
            // 처음은 동쪽으로 ㄲ , 굴린 자리가 넘어가면 방향 반대로 설정
            goNext();

            // 아랫면 정수 a와 주사위의 칸 수 비교해 이동방향 정하기
            int A = dice[2];
            int B = map[curX][curY];

            if (A > B) {
                dir = (dir + 1) % 4;
            } else if (A < B) {
                dir -= 1;
                if (dir == -1) {
                    dir = 3;
                }
            }
            // 같을 경우 방향 그대로 ㄲㄲㄲ

            // (움직여서 도착한 칸에있는 숫자 * 그 숫자와 동일한 숫자들) bfs로 구하고 최종값에 더해주기
            result += bfs();
        
        }
        System.out.println(result);
    }

    public static int bfs() {
        int ans = 0;

        int[] ddx = { 1, 0, -1, 0 };
        int[] ddy = { 0, 1, 0, -1 };

        Deque<Node> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];

        int bottomNum = map[curX][curY];
        q.add(new Node(curX, curY));
        visited[curX][curY] = true;

        while (!q.isEmpty()) {
            Node tmp = q.poll();
            ans++;

            for (int i = 0; i < 4; i++) {
                int nx = tmp.x + ddx[i];
                int ny = tmp.y + ddy[i];

                // 넘거나, 방문했거나, 동일 숫자아니면 패스
                if (check(nx, ny) || visited[nx][ny] || map[nx][ny] != bottomNum) {
                    continue;
                }

                q.add(new Node(nx, ny));
                visited[nx][ny] = true;
            }
        }
        return ans * bottomNum;
    }

    public static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void goNext() {
        // 다음 자리로 간다. 굴린 자리가 맵 넘어가면 방향 반대로 설정
        int nx = curX + dx[dir];
        int ny = curY + dy[dir];

        // 굴린곳이 맵에서 넘어가면
        if (check(nx, ny)) {
            // 방향 바꾸기
            dir = (dir + 2) % 4;
            // 넘어가는 자리 다시 설정
            nx = curX + dx[dir];
            ny = curY + dy[dir];
        }
        // 방향에 맞게 다이스 재배치
        changeDice();

        // 현재 자리 설정
        curX = nx;
        curY = ny;
    }

    public static boolean check(int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= M) {
            return true;
        }
        return false;
    }

    // 주사위 이동시마다 바뀔 위치 작성
    public static void changeDice() {
        int[] newDice = new int[6];
        // 동쪽으로 이동
        if (dir == 0) {
            newDice[0] = dice[0];
            newDice[1] = dice[2];
            newDice[2] = dice[3];
            newDice[3] = dice[5];
            newDice[4] = dice[4];
            newDice[5] = dice[1];
        }
        // 남
        else if (dir == 1) {
            newDice[0] = dice[2];
            newDice[1] = dice[1];
            newDice[2] = dice[4];
            newDice[3] = dice[3];
            newDice[4] = dice[5];
            newDice[5] = dice[0];
        }
        // 서
        else if (dir == 2) {
            newDice[0] = dice[0];
            newDice[1] = dice[5];
            newDice[2] = dice[1];
            newDice[3] = dice[2];
            newDice[4] = dice[4];
            newDice[5] = dice[3];
        }
        // 북
        else {
            newDice[0] = dice[5];
            newDice[1] = dice[1];
            newDice[2] = dice[0];
            newDice[3] = dice[3];
            newDice[4] = dice[2];
            newDice[5] = dice[4];
        }

        dice = newDice;
    }
}
