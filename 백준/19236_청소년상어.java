import java.io.*;
import java.util.*;

public class Main {
    // 0~7까지 방향
    static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
    static int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };
    static int max = 0;

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Fish[][] map = new Fish[4][4];
        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                int id = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                // 살았는지 까지 확인
                map[i][j] = new Fish(id, dir - 1, false);
            }
        }
        // 상어 현재 x위치, 상어 현재 y위치, 첫방향, 물고기 들어있는 map, 총합
        moveShark(0, 0, -1, map, 0);
        System.out.println(max);
    }

    public static void moveShark(int x, int y, int dir, Fish[][] map, int sum) {
        // 상어가 현재자리 물고기 잡아먹기
        dir = map[x][y].dir % 8;// 상어 방향 습득
        map[x][y].dead = true;// 죽음 처리
        // System.out.println("x : " + x + " , y : " + y + " , dir : " + dir + " , sum :
        // " + sum);
        sum += map[x][y].id;

        // 가장 큰 값 찾기
        max = Math.max(max, sum);

        // 물고기 이동
        moveFish(map, x, y);// 물고기 이동시 현재 상어 위치와 물고기 맵과 같이 필요로 하는 정보들 같이 넘김

        // 앞의 물고기들 확인하고 다시 돌리기
        // 맵이 가로세로 4칸씩임
        for (int i = 1; i < 4; i++) {
            // 상어 앞으로 움직임
            int nx = x + dx[dir] * i;
            int ny = y + dy[dir] * i;
            // System.out.println("nx : " + nx + " , ny : " + ny);

            // 앞으로 간곳이 벽이면 끝
            if (check(nx, ny)) {
                break;
            }
            // 앞으로 간곳에 물고기 죽어있으면 그냥 패스하고 다음 위치로
            if (map[nx][ny].dead) {
                continue;
            }

            // 맵 중복안되게 분기마다 초기화
            Fish[][] newMap = new Fish[4][4];
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    // newMap[j][k] = map[j][k]; -> 새로 생성하는게 아니라 바뀐 내용이 넣어짐
                    // 새로 물고기 만들어서 넣어야함!!!!
                    newMap[j][k] = new Fish(map[j][k].id, map[j][k].dir, map[j][k].dead);
                }
            }

            // 해당 위치에서 분기 시작
            moveShark(nx, ny, dir, newMap, sum);
        }
    }

    public static void moveFish(Fish[][] map, int x, int y) {
        // 물고기 번호만큼 돌기
        for (int i = 1; i <= 16; i++) {
            go(map, x, y, i);// 몰고기 맵, 상어 현재 위치, 상어 현재 y위치 , 움직일 물고기
        }
    }

    public static void go(Fish[][] map, int x, int y, int id) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // 돌면서 번호 같은 물고기 있고, 아직 살아있는지 확인
                if (map[i][j].id == id && !map[i][j].dead) {
                    // 살아있으면 이동
                    int cnt = 0;
                    while (cnt < 8) {
                        int way = map[i][j].dir % 8;
                        int nx = i + dx[way];
                        int ny = j + dy[way];

                        // 벽 넘거나 해당 자리에 상어가 있으면 방향 바꾸기 ->
                        // 방향은 자기자리 제외 7번 바꾸기 가능
                        if (check(nx, ny) || (x == nx && y == ny)) {
                            map[i][j].dir++;
                            // 방향 증가
                            cnt++;
                            // 8번이상 방향바꾸기 금지
                            continue;
                        }
                        // 자리 스왑
                        Fish tmp = map[nx][ny];
                        map[nx][ny] = map[i][j];
                        map[i][j] = tmp;
                        return;
                    }
                }
            }
        }
    }

    public static boolean check(int x, int y) {
        if (x < 0 || x >= 4 || y < 0 || y >= 4) {
            return true;
        }
        return false;
    }
}

class Fish {
    int id;// 물고기 숫자
    int dir;// 물고기 방향
    boolean dead;// 물고기 죽었는지 여부

    public Fish(int id, int dir, boolean dead) {
        this.id = id;
        this.dir = dir;
        this.dead = dead;
    }
}