import java.io.*;
import java.util.*;

public class Solution {
    static Deque<Belt> up_dq;
    static Deque<Belt> down_dq;
    static int N, K, zero;
    static int cnt = 1;

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());// k개 이상 0 이면 끝

        // 상위 벨트
        up_dq = new LinkedList<>();
        // 하위 벨트
        down_dq = new LinkedList<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {// 012
            int n = Integer.parseInt(st.nextToken());
            up_dq.addLast(new Belt(n, false));// n값 , 내구도, 로봇 여부
        }
        for (int i = N; i < N * 2; i++) {// 543
            int n = Integer.parseInt(st.nextToken());
            down_dq.addFirst(new Belt(n, false));
        }

        // 로봇 옮기는 과정
        while (true) {
            // 1. 상하 큐 회전 & 로봇 이동
            moveBelt();

            // 2. 조건에 따라 로봇들 한칸씩 이동
            moveRobot();

            // 3. 올리는 위치 내구도 0 아니면 로봇 올리기
            if (up_dq.peek().weight != 0) {
                up_dq.peek().hasRobot = true;
                up_dq.peek().weight -= 1;
            }
            // 4. 내구도 0칸 개수 k개 이상이면 종료 -> 아니면 다시 1번 부터 시작
            if (checkTotalZero()) {
                break;
            }

            cnt++;
        }

        // 단계 출력
        System.out.println(cnt);
    }

    public static void moveBelt() {
        // 상위 큐 빼서 다음 큐에 넣기
        down_dq.addLast(up_dq.pollLast());
        // 하위 큐에서 첫번쨰 빼서 상위큐에 넣기
        up_dq.addFirst(down_dq.pollFirst());
    }

    public static void moveRobot() {

        // 상위 큐에서만 이동하면 됌
        for (int i = 0; i < up_dq.size(); i++) {
            // 가장 먼저 올라간 로봇부터 움직여야함 -> 뒤쪽부터 검사
            Belt b = up_dq.pollLast();

            // 마지막위치면
            if (i == 0) {
                // 로봇 없음으로 체크 -> 로봇 내리기
                b.hasRobot = false;
            }
            // 로봇 이동 (다음칸에 로봇 없고 내구도 1이상 ㅇㅇ)
            // 마지막 위치가 아니면
            else if (b.hasRobot == true && up_dq.peek().hasRobot == false && up_dq.peek().weight >= 1) {
                up_dq.peek().weight -= 1;
                up_dq.peek().hasRobot = true;

                // 현재는 로봇 없애기
                b.hasRobot = false;
            }
            // 로봇 못 움직이면 그대로 있기

            up_dq.addFirst(b);
        }

        // 하위큐의 로봇 여부 모두 없애기
        for (int i = 0; i < down_dq.size(); i++) {
            Belt b = down_dq.pollFirst();
            b.hasRobot = false;
            down_dq.addLast(b);
        }
    }

    public static boolean checkTotalZero() {
        zero = 0;

        for (int i = 0; i < N; i++) {
            Belt up_b = up_dq.pollFirst();
            Belt down_b = down_dq.pollFirst();

            if (up_b.weight == 0) {
                zero++;
            }
            if (down_b.weight == 0) {
                zero++;
            }

            if (zero >= K) {
                return true;
            }

            up_dq.addLast(up_b);
            down_dq.addLast(down_b);
        }
        return false;
    }
}

class Belt {
    int weight;// 내구도
    boolean hasRobot;// 로봇 가지고 있는지 여부

    public Belt(int weight, boolean hasRobot) {
        this.weight = weight;
        this.hasRobot = hasRobot;
    }
}