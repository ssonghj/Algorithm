import java.io.*;
import java.util.*;

public class Main {
    // 도형 전체 값 넣기
    // 15, 4, 2
    static int[][][] arr = {
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } }, { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } },

            { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },

            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } }, { { 2, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } },
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 } }, { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } },

            { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } }, { { 0, 1 }, { 1, 0 }, { 2, 0 }, { 1, 1 } },
            { { 0, 1 }, { 0, 2 }, { 1, 0 }, { 1, 1 } }, { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 1, 2 } },

            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } }, { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } },
            { { 1, 0 }, { 1, 1 }, { 0, 1 }, { 2, 1 } }, { { 1, 0 }, { 1, 1 }, { 1, 2 }, { 0, 1 } },

            { { 0, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 1, 2 } },
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } }, { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 } }
    };
    static int N, M;
    static int maxSum;

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 배열 하나씩 다 돌면서 값 더해서 최종 합과 비교해보기 -> 크면 교체
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // 모서리 네곳 증가시킨 값이 배열 범위 넘으면 안된다.

                // 하드코딩된 테트로미노 값들
                for (int k = 0; k < 19; k++) {
                    int tmpSum = 0;
                    boolean flag = false;
                    for (int m = 0; m < 4; m++) {
                        int x = arr[k][m][0] + i;
                        int y = arr[k][m][1] + j;

                        // 범위넘었으면 이부분 필요 없음
                        if (check(x, y)) {
                            flag = true;
                            break;
                        }
                        // 넘어갔으면 값 넣기
                        tmpSum += map[x][y];
                    }

                    // 모두 돌아서 나온거면 비교해서 큰값 ㄲ
                    if (flag == false) {
                        maxSum = Math.max(tmpSum, maxSum);
                    }
                }
            }
        }
        System.out.println(maxSum);
    }

    public static boolean check(int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= M) {
            return true;
        }
        return false;
    }
}