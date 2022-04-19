import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    static int N, move, dir, s;
    static int[][] board, cloudArr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        move = Integer.parseInt(st.nextToken());

        board = new int[N + 1][N + 1];
        cloudArr = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j <= N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 초기 구름위치
        cloudArr[N][1] = 1;
        cloudArr[N][2] = 1;
        cloudArr[N - 1][1] = 1;
        cloudArr[N - 1][2] = 1;

        // 비바라기 시작
        for (int i = 0; i < move; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            dir = Integer.parseInt(st.nextToken()) - 1; // 방향 -1
            s = Integer.parseInt(st.nextToken());

            result();
        }

        int cnt = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                cnt += board[i][j];
            }
        }
        System.out.println(cnt);
    }

    public static void result() {
        // 구름 움직이고 구름있는 칸의 바구니 물 양 + 1
        moveCloud();

        // 2에서 증가한 칸에 물복사버그 마법 ㄲ -> 대각선 방향 거리 1인칸에 물 있으면 개수만큼 물양 증가
        // 경계의 값은 패스
        duplicateWater();

        // 바구니의 물 양이 2이상이고 이전에 구름있던칸이 아니면 물의양 -2 하고 구름 생성
        // 새로운 배열 생성해서 구름위치 담고 cloudArr에 복사
        generateCloud();

    }

    public static void generateCloud() {
        int[][] genCloud = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // 물양 2 이상이고, 이전에 구름있던칸 아니면 물양 -2 하고 구름 ㅇㅇ
                if (board[i][j] >= 2 && cloudArr[i][j] != 1) {
                    board[i][j] -= 2;
                    genCloud[i][j] = 1;
                }
            }
        }

        // 새로운 배열 생성해서 구름위치 담고 cloudArr에 복사
        cloudArr = genCloud;
    }

    public static void moveCloud() {
        int[][] tmpCloud = new int[N + 1][N + 1];

        int[] dx = { 0, -1, -1, -1, 0, 1, 1, 1 };
        int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // 구름 위치가 1이면
                if (cloudArr[i][j] == 1) {
                    // 현재위치 + 이동할 방향의 좌표 * 이동거리
                    int nx = (i + dx[dir] * s);// 이어짐
                    int ny = (j + dy[dir] * s);

                    while (nx <= 0) {
                        nx += N;
                    }
                    while (ny <= 0) {
                        ny += N;
                    }       
                    while (nx > N) {
                        nx -= N;
                    }
                    while (ny > N) {
                        ny -= N;
                    }
    
                    tmpCloud[nx][ny] = 1;// 구름이동한 곳 위치 저장
                    board[nx][ny] += 1; // 물 양 + 1
                }
            }
        }
        // 구름 다 움직였으면 움직인 구름위치 복사
        cloudArr = tmpCloud;
    }

    public static void duplicateWater() {
        int[] dx = { -1, -1, 1, 1 };
        int[] dy = { -1, 1, -1, 1 };

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {

                // 움직인 구름위치
                if (cloudArr[i][j] == 1) {
                    // 대각선 유효한지 확인
                    int cnt = 0;
                    for (int k = 0; k < 4; k++) {
                        int nx = i + dx[k];
                        int ny = j + dy[k];

                        // 대각선 유효하고 물이 있으면 물복사!!!!
                        if (isDiagonal(nx, ny)) {
                            continue;
                        }
                        
                        if (board[nx][ny] <= 0) {
                            continue;
                        }
                        cnt++;
                    }
                    board[i][j] += cnt;
                }
            }
        }
    }

    public static boolean isDiagonal(int x, int y) {
        // 대각선범위내에 없으면
        if (x <= 0 || x > N || y <= 0 || y > N) {
            return true;
        }
        return false;
    }
}
