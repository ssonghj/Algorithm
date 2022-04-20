import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    static int N, X, Y, size, time;
    static int[][] board;
    static boolean[][] visited;
    static PriorityQueue<Node> pq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        board = new int[N][N];
        size = 2;// 상어 사이즈
        time = 0;//시간

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {

                board[i][j] = Integer.parseInt(st.nextToken());
                if (board[i][j] == 9) {
                    X = i;// 초기 시작 위치
                    Y = j;
                    board[i][j] = 0; // 물고기 자리는 비움
                }
            }
        }

        int eatFishCnt = 0;//먹은 물고기 수 
        while (true) {
            pq = new PriorityQueue<>(); // 한턴마다 물고기 담을 pq생성 
            visited = new boolean[N][N];

            //먹을 물고기 찾기 수행 
            bfs();

            //bfs후 물고기 먹을 거 있는지 확인
            if (pq.size() != 0) {

                // pq에서 가장 앞의 물고기 먹기 -> 거리기반 x기반 y기반 비교 후 넣어져 있음
                Node n = pq.peek();

                time += n.count;

                // 먹고 물고기 위치 갱신 및 물고기 없애기
                X = n.x;
                Y = n.y;
                board[n.x][n.y] = 0;

                eatFishCnt++;
                // 자기 사이즈만큼 물고기 먹었으면 사이즈+1, 먹은 수 0
                if (eatFishCnt == size) {
                    size++;
                    eatFishCnt = 0;
                }
            }
            // 물고기 먹을 거 없으면 끝
            else {
                break;
            }
        }
        System.out.println(time);
    }

    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    public static void bfs() {
        Deque<Node> dq = new LinkedList<>();
        visited[X][Y] = true;
        dq.add(new Node(X, Y, 0));

        while (!dq.isEmpty()) {
            Node cur = dq.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (check(nx, ny) || board[nx][ny] > size || visited[nx][ny]) {
                    continue;
                }

                visited[nx][ny] = true;
                dq.add(new Node(nx, ny, cur.count + 1));

                if (board[nx][ny] < size && board[nx][ny] != 0) {
                    pq.add(new Node(nx, ny, cur.count + 1));
                }
            }
        }

    }

    public static boolean check(int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= N) {
            return true;
        }
        return false;
    }
}

class Node implements Comparable<Node> {
    int x;
    int y;
    int count;

    public Node(int x, int y, int count) {
        this.x = x;
        this.y = y;
        this.count = count;
    }

    // 우선순위
    // 1.거리 2. x작음 3. y작음
    @Override
    public int compareTo(Node o) {
        // 1. 거리 똑같을 경우
        if (this.count - o.count == 0) {
            // 2. x가 똑같을 경우
            if (this.x - o.x == 0) {
                return this.y - o.y;
            } else {
                return this.x - o.x;
            }
        } else {
            return this.count - o.count;
        }

    }
}