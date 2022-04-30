import java.io.*;
import java.util.*;

class Solution {
    static int N, M, k;
    static Shark[][] map;
    static int[][][] priority;
    static int[] dx = { -1, 1, 0, 0 };// 상하 좌우
    static int[] dy = { 0, 0, -1, 1 };
    // 상어 이동하면 해당 자리 저장
    static int[][] tmpSharkMovePosition;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());// 격자판
        M = Integer.parseInt(st.nextToken());// 상어 수
        k = Integer.parseInt(st.nextToken());// 냄새 빠지는 횟수

        map = new Shark[N][N];
        // 상어 자리 받기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int sharkNum = Integer.parseInt(st.nextToken());
                if (sharkNum == 0) {
                    // 상어 자리 아닐 때 방향 -1
                    map[i][j] = new Shark(sharkNum, 0, -1);
                } else {
                    // 상어 자리 일때
                    map[i][j] = new Shark(sharkNum, k, 0);
                }
            }
        }

        // 상어방향 추가해주기
        // 상어 자리별로 일단 받기
        String[] tmp = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 상어 있을 때
                if (map[i][j].sharkNum != 0) {
                    // 상어 번호 따라 방향 추가해 주기
                    // 상어 번호 빼기 -1 == tmp에 들어있는 상어 위치
                    map[i][j].dir = Integer.parseInt(tmp[map[i][j].sharkNum - 1]);
                }
            }
        }

        // 우선순위 출력
        // 상어 번호, 방향 1 2 3 4, 우선순위4곳
        priority = new int[M + 1][4 + 1][4];
        for (int i = 1; i <= M; i++) {
            // 상어당 위,아래,왼쪾, 오른쪽
            for (int j = 1; j <= 4; j++) {
                // 상어 우선순위
                st = new StringTokenizer(br.readLine());
                for (int m = 0; m < 4; m++) {
                    priority[i][j][m] = Integer.parseInt(st.nextToken());
                }
            }
        }

        int cnt = 1;
        while (cnt <= 1000) {
            // 상어 이동
            moveShark();

            // 상어 한마리 남았는지 확인
            boolean check = checkSharkNum();
            // 한마리 남았으면 해당 초 리턴하고 끝
            if (check) {
                System.out.println(cnt);
                return;
            }

            // 1000초 넘으면 -1 출력
            cnt++;
        }

        System.out.println(-1);
    }

    public static void moveShark() {
        // 상어 기존 위치 0 1,이동위치 2 3 , 방향 4 저장

        // 죽은 상어는 비워야함
        tmpSharkMovePosition = new int[M + 1][4 + 1]; // 움직인 상어들 위치 저장 용
        for (int i = 0; i < tmpSharkMovePosition.length; i++) {
            for (int j = 0; j < tmpSharkMovePosition[i].length; j++) {
                tmpSharkMovePosition[i][j] = -1;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 상어면 이동 로직 ㄱ
                if (map[i][j].sharkNum > 0 && map[i][j].dir != -1) {
                    // 인접칸 중 냄새 없는 칸이면 이동
                    boolean isMove = moveIfNoSmell(i, j);

                    // 인접칸에 냄새가 모두 있어서 움직이지 못했다면
                    if (!isMove) {
                        moveWithSmell(i, j);
                    }

                }
            }
        }
        // 위치에 따라 상어 이동 ㄲ
        // 기존 위치는 샤크번호는 남기고, 냄새 맥스로 남기고, + 방향 -1로 만들기 ->먼저 다 하기
        for (int i = 1; i < tmpSharkMovePosition.length; i++) {
            // 죽은 상어 패스
            if (tmpSharkMovePosition[i][1] == -1) {
                continue;
            }
            // 이전 위치들
            int beforeX = tmpSharkMovePosition[i][0];
            int beforeY = tmpSharkMovePosition[i][1];

            map[beforeX][beforeY].remainCnt = k;
            map[beforeX][beforeY].dir = -1;
        }

        // 기존 상어 위치는 지운 상태
        // 상어 이동 후 그 칸에 다른 상어 있으면 번호 작은 상어가 차지 -> 다음에 다 하기
        for (int i = 1; i < tmpSharkMovePosition.length; i++) {
            // 죽은 상어패스

            if (tmpSharkMovePosition[i][1] == -1) {
                continue;
            }
            int afterX = tmpSharkMovePosition[i][2];
            int afterY = tmpSharkMovePosition[i][3];
            int afterDir = tmpSharkMovePosition[i][4];

            // 이동한 곳에 샤크가 있다면
            if (map[afterX][afterY].sharkNum > 0 && map[afterX][afterY].dir != -1) {
                // num비교해서 더 작은 애가 넣어지게 만들기

                // 현재 위치의 값이 더 크면
                if (map[afterX][afterY].sharkNum > i) {
                    // 샤크 넘버, 냄새 ,방향,
                    map[afterX][afterY].sharkNum = i;
                    map[afterX][afterY].remainCnt = k;
                    map[afterX][afterY].dir = afterDir;
                }
                // 원래 있던 애가 더 작으면 그냥 넘어가기
                continue;
            }
            // 이동한 곳에 샤크 없으면
            map[afterX][afterY] = new Shark(i, k, afterDir);
        }
        // 모든 상어 움직인 후 전체 냄새하나씩 없애기
        removeSmell();
    }

    // 자기 냄새 있는 칸 중 우선순위 정해서 이동
    public static void moveWithSmell(int x, int y) {

        Shark shark = map[x][y];

        // 인접한 칸 중 자기 냄새있는 칸으로 우선 순위 따라 이동
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[priority[shark.sharkNum][shark.dir][i] - 1];
            int ny = y + dy[priority[shark.sharkNum][shark.dir][i] - 1];

            // 벽 넘으면 패스 , 자기 냄새 아닌 곳 패스
            if (checkWall(nx, ny) || checkNotMySmell(nx, ny, shark.sharkNum)) {
                continue;
            }

            // 어차피 빈곳은 없으므로 냄새 있는 곳 중 가능한 곳으로 고고
            // 이동 가능한 곳
            tmpSharkMovePosition[shark.sharkNum][0] = x;
            tmpSharkMovePosition[shark.sharkNum][1] = y;
            tmpSharkMovePosition[shark.sharkNum][2] = nx;
            tmpSharkMovePosition[shark.sharkNum][3] = ny;
            tmpSharkMovePosition[shark.sharkNum][4] = priority[shark.sharkNum][shark.dir][i];
            return;
        }

    }

    // 인접칸에 자기 냄새 있는지 없는 지 확인
    public static boolean checkNotMySmell(int x, int y, int curSmell) {
        Shark goShark = map[x][y];
        if (goShark.sharkNum != curSmell) {
            // 자기 냄새아니면
            return true;
        }
        // 자기 냄새면
        return false;
    }

    // 인접칸에 냄새 있는지 없는지 확인
    public static boolean moveIfNoSmell(int x, int y) {
        // 현재 샤크
        Shark shark = map[x][y];
        // 인접칸에 냄새없으면 이동
        for (int i = 0; i < 4; i++) {
            // 우선순위에 따라 위치 이동
            // 샤크 넘버, 샤크 현재 방향에따른 , 샤크 이동순위 방향
            int nx = x + dx[priority[shark.sharkNum][shark.dir][i] - 1];
            int ny = y + dy[priority[shark.sharkNum][shark.dir][i] - 1];

            // 방향 넘거나, 해당 자리에 냄새있으면 패스
            if (checkWall(nx, ny) || checkSmell(nx, ny)) {
                continue;
            }

            // 상어 기존 위치 저장
            tmpSharkMovePosition[shark.sharkNum][0] = x;
            tmpSharkMovePosition[shark.sharkNum][1] = y;
            // 이동 가능하면 이동할 자리 저장 후 리턴 -> 우선순위기 때문에 저장하면 그냥 저장하고 나오면 됌
            tmpSharkMovePosition[shark.sharkNum][2] = nx;
            tmpSharkMovePosition[shark.sharkNum][3] = ny;
            // 이동방향도 저장
            tmpSharkMovePosition[shark.sharkNum][4] = priority[shark.sharkNum][shark.dir][i];
            return true;
        }

        // 인접칸에 모두 냄새있으면 false출력
        return false;
    }

    // 벽 넘었는지 체크
    public static boolean checkWall(int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= N) {
            return true;
        }
        return false;
    }

    // 해당 자리에 냄새 있는지 체크
    public static boolean checkSmell(int x, int y) {
        if (map[x][y].sharkNum > 0 || map[x][y].remainCnt > 0) {
            return true;
        }
        return false;
    }

    // 냄새지우기
    public static void removeSmell() {
        // 상어 없는 칸의 냄새 하나씩 줄이기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 해당 자리에 상어는 없지만 냄새는 남은 경우
                if (map[i][j].sharkNum > 0 && map[i][j].dir == -1) {
                    map[i][j].remainCnt -= 1;
                }
                // 만약 냄새가 0이 되었으면 해당 자리 비우기
                if (map[i][j].remainCnt == 0) {
                    map[i][j] = new Shark(0, 0, -1);
                }
            }
        }
    }

    // 상어 숫자 확인
    public static boolean checkSharkNum() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j].sharkNum > 0 && map[i][j].dir != -1) {
                    cnt++;
                }
                if (cnt > 1) {
                    return false;
                }
            }
        }

        return true;
    }
}

class Shark {
    int sharkNum;
    int remainCnt;
    int dir;

    public Shark(int sharkNum, int remainCnt, int dir) {
        this.sharkNum = sharkNum;
        this.remainCnt = remainCnt;
        this.dir = dir;
    }
}