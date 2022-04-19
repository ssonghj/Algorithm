import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {
    static int N;
    static int[][] board;
    static Deque<Node> dq;
    static int time;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        board = new int[N + 1][N + 1]; // 주의

        answer = 0;
        dq = new LinkedList<>();

        int K = Integer.parseInt(br.readLine());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            // 1은 사과
            board[n1][n2] = 1;
        }

        Node node = new Node(1, 1);
        dq.add(node);

        boolean check = false;
        int L = Integer.parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int X = Integer.parseInt(st.nextToken());
            String C = st.nextToken();

            // 뱀 진행 -> 큐쓰면 될듯
            check = calulate(X, C);
            // true 면 죽음
            if (check) {
                break;
            }
        }
        // 리턴 false인데 마지막이면 끝까지 움직여야하므로
        if (!check) {
            while (true) {
                Node cur = dq.peek();
                // 매초마다 보고 있는 뱡향으로 1칸씩 움직임
                int nx = dx[dir] + cur.x;
                int ny = dy[dir] + cur.y;
                answer++;

                // 죽을때까지 움직임
                if (isWall(nx, ny) || isBody(nx, ny)) {
                    break;
                }

                // 사과먹으면 몸 늘어남 -> 큐 빼지 않기
                if (board[nx][ny] == 1) {
                    board[nx][ny] = 0;
                    dq.addFirst(new Node(nx, ny));
                }
                // 사과 안먹으면 한칸 앞으로가고 이전애 있던 자리는 빠짐 -> 맨뒤 큐 빼기
                else {
                    dq.addFirst(new Node(nx, ny));
                    dq.pollLast();
                }
            }
        }

        System.out.println(answer);
    }

    // 00 01
    // 10 11
    static int[] dx = { -1, 1, 0, 0 };// 상하좌우
    static int[] dy = { 0, 0, -1, 1 };
    static int dir = 3; // 처음은 우측으로 ㄲㄲㄲㄲ

    public static boolean calulate(int X, String C) {
        time = answer;
        for (int i = 0; i < X - time; i++) {
            // System.out.println("X : " + X + ", answer : " + answer + ", time : " + time);
            // 현재 위치
            Node cur = dq.peek();
            // 매초마다 보고 있는 뱡향으로 1칸씩 움직임
            int nx = dx[dir] + cur.x;
            int ny = dy[dir] + cur.y;
            // System.out.println("nx : " + nx + ", ny : " + ny + ", pq :" + dq.size());
            // System.out.println();

            // 벽이나 자기 몸 닿으면 죽음
            if (isWall(nx, ny) || isBody(nx, ny)) {
                answer++;
                return true;
            }

            // 사과먹으면 몸 늘어남 -> 큐 빼지 않기
            if (board[nx][ny] == 1) {
                board[nx][ny] = 0;
                dq.addFirst(new Node(nx, ny));
            }
            // 사과 안먹으면 한칸 앞으로가고 이전애 있던 자리는 빠짐 -> 맨뒤 큐 빼기
            else {
                dq.addFirst(new Node(nx, ny));
                dq.pollLast();
            }

            // 초 증가
            answer++;
        }
        // 방향 변경
        changeDir(C);

        // 안죽고 살음
        return false;
    }

    static void changeDir(String C) {
        if (dir == 0) {// 상
            // 오른쪽
            if (C.equals("D")) {
                dir = 3;
            } else {
                dir = 2;
            }
        } else if (dir == 1) {// 하
            if (C.equals("D")) {
                dir = 2;
            } else {
                dir = 3;
            }
        } else if (dir == 2) {// 좌
            if (C.equals("D")) {
                dir = 0;
            } else {
                dir = 1;
            }
        } else {// 우
            if (C.equals("D")) {
                dir = 1;
            } else {
                dir = 0;
            }
        }
    }

    static boolean isBody(int x, int y) {
        for (Node n : dq) {
            // 몸에 부딪히면 나가리
            if (n.x == x && n.y == y) {
                return true;
            }
        }

        return false;
    }

    static boolean isWall(int x, int y) {
        if (x <= 0 || x > N || y <= 0 || y > N) {
            return true;
        }
        return false;
    }
}

class Node {
    int x;
    int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}