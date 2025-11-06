// Time Complexity : O(n^2)
// Space Complexity : O(n^2)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

/**
 * Flatten the 2d board into 1d array so we don't have to add the logic for going zig zag.
 * Start at first cell and roll the dice for all numbers between 1 to 6
 *  Mark the cell visited
 *  If the current cell was already visited, then don't visit again since it won't give minimum number of steps.
 *  If it was a snake or a ladder, then go to the destination index.
 * At the end of all moves, return -1 since we never reached the end.
 */
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] arr = new int[n * n];
        boolean flag = true; // true means reading the board from left to right
        int c = 0;
        int r = n - 1;
        // flattening the 2d board into 1d array since we have to traverse 0 -> n and n -> 0 in consecutive rows
        for (int idx = 0; idx < n * n; idx++) {
            if (board[r][c] == -1)
                arr[idx] = -1;
            else
                arr[idx] = board[r][c] - 1;
            if (flag) {
                c++;
                if (c == n) {
                    c--;
                    r--;
                    flag = false;
                }
            } else {
                c--;
                if (c == -1) {
                    c++;
                    r--;
                    flag = true;
                }
            }
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        int moves = 0;
        while (!q.isEmpty()) {
            // getting the size sicne we have to return the minimum number of steps to reach the destination
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int curr = q.poll();

                // 6 possibilities for rolling the dice
                for (int j = 1; j <= 6; j++) {
                    int newIdx = curr + j;

                    // we have reached the end either via rolling the dice from [n-1, n-6] location OR there is a ladder to the destination
                    if (newIdx >= n * n - 1 || arr[newIdx] >= n * n - 1)
                        return moves + 1;
                    // if not visited
                    if (arr[newIdx] != -2) {
                        // normal unvisited cell
                        if (arr[newIdx] == -1) {
                            q.add(newIdx);
                        }
                        // either ladder or snake, go the end destination
                        else if (arr[newIdx] > 0) {
                            q.add(arr[newIdx]);
                        }
                        // mark that cell visited since we need least number of steps and subsequent visit to this cell won't get minimum steps
                        arr[newIdx] = -2;
                    }
                }
            }
            moves++;
        }

        // couldn't reach the destination
        return -1;
    }
}