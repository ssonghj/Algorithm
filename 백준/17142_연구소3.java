import java.io.*;
import java.util.*;

public class Solution {
    static int N, M;
    static int[][] arr;
    static Virus[] active;
    static int originEmptySpace = 0;//0개수 저장용 
    static List<Virus> viruses = new ArrayList<>();//바이러스 위치 저장용 
    static int resultMinTime = Integer.MAX_VALUE;
    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N][N];
        active = new Virus[M];//조합 시 활성화된 바이러스 위치 파악 용 

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());

                if (arr[i][j] == 0) {
                    //비활성 바이러스 때문에 증가하는 곳 체크하기위해 빈 공간 세놓음 
                    originEmptySpace++;
                } else if (arr[i][j] == 2) {
                    //바이러스 위치 및 시간초기화 저장 
                    viruses.add(new Virus(i, j, 0));
                }
            }
        }

        //이동할 공간이 없으면 0리턴 
        if (originEmptySpace == 0) {
            System.out.println(0);
        } else {
            //바이러스 고르기 
            selectVirus(0, 0);
            //최소 시간 혹은 이동 못함 출력 
            System.out.println(resultMinTime == Integer.MAX_VALUE ? -1 : resultMinTime);
        }
    }
    
    //백트래킹으로 조합 구현 
    public static void selectVirus(int start, int selectCnt) {
        //선택한게 3개가 되었으면 퍼뜨리러가기 
        if (selectCnt == M) {
            //bfs로 들어간다.
            spreadVirus(originEmptySpace);
            return;
        }

        //한바퀴 돌기 
        for (int i = start; i < viruses.size(); i++) {
            //활성화 배열은 활성화할 virus개수만큼으로 정해져있음 -> 3개선택이면 배열크기 3 
            //재귀통해 들어온 selectCnt에 i자리의 virus 객체를 넣어줌 
            active[selectCnt] = viruses.get(i);
            selectVirus(i + 1, selectCnt + 1);
        }
    }

    static void spreadVirus(int emptySpace) {
        Queue<Virus> q = new LinkedList<>();
        boolean[][] infected = new boolean[N][N];

        for (int i = 0; i < M; i++) {
            //활성화된 바이러스 객체 가져와서 
            Virus virus = active[i];
            //감염 완료 
            infected[virus.x][virus.y] = true;
            //큐에 넣어서 bfs돌릴 준비 
            q.add(virus); // 활성화할 개수인 3개 넣어준다. 
        }

        while (!q.isEmpty()) {
            Virus virus = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = virus.x + dx[i];
                int ny = virus.y + dy[i];

                //벽 넘어가면 패스 
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                    continue;
                }
                //감염된 자리거나 벽이면 패스 
                if (infected[nx][ny] || arr[nx][ny] == 1) {
                    continue;
                }

                //퍼뜨릴 공간-1
                if (arr[nx][ny] == 0) {
                    emptySpace--;
                }

                //만약 감염할 공간이 0 되면 비활성바이러스 -> 활성바이러스 되는 시간 세지 않아도 됌.
                //비활성바이러스 지나갈 수 있는 여건 고려하는 조건  
                if (emptySpace == 0) {
                    //최소시간 = 현재 최소 값과 virus.time +1 값 비교 
                    resultMinTime = Math.min(resultMinTime, virus.time + 1);
                    return;
                }

                infected[nx][ny] = true;//감염완료 
                q.add(new Virus(nx, ny, virus.time + 1));//큐에 새로운 바이러스 객체 추가 
            }
        }
    }
}

class Virus {
    int x, y, time;

    public Virus(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}