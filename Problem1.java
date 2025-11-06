// Time Complexity : O(m*n)
// Space Complexity : O(m*n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

/**
 * If the mine is clicked, the game is over and we can return the board.
 * If we click on unrevealed cell, we get the count of mines at that cell.
 *  If the count is 0, then we can mark the cell to be revealed, process the neighbors and potentially reveal more connected components.
 *  If the count is greater than 0, then we can't reveal any more cells and just set the mine count to this cell.
 */
class Solution {
    int[][] dirs = {{-1, 0}, // up
            {1, 0}, // down
            {0, -1}, // left
            {0, 1}, // right
            {-1, -1}, // up-left
            {-1, 1}, // up-right
            {1, -1}, // down-left
            {1, 1} // down-right
    };

    public char[][] updateBoard(char[][] board, int[] click) {
        // clicked on a mine, finish the game
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        Queue<int[]> q = new LinkedList<>();
        q.add(click);
        board[click[0]][click[1]] = 'B';
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int mines = mineCount(board, curr);
            // if there are not any mines around current location, we can reveal more spots
            if (mines == 0) {
                for (int[] dir : dirs) {
                    int nr = curr[0] + dir[0];
                    int nc = curr[1] + dir[1];

                    // no mines in neighbor and the cell is unrevealed so we can reveal it
                    if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && board[nr][nc] == 'E') {
                        q.add(new int[]{nr, nc});
                        board[nr][nc] = 'B';
                    }
                }
            }
            // set the mine count and don't process any neighbors since they can't be revealed
            else {
                board[curr[0]][curr[1]] = (char) (mines + '0');
            }
        }

        return board;
    }

    // returns the neighbor mine count on current location using all adjacent cells
    private int mineCount(char[][] board, int[] location) {
        int cnt = 0;
        for (int[] dir : dirs) {
            int nr = location[0] + dir[0];
            int nc = location[1] + dir[1];

            if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && board[nr][nc] == 'M') {
                cnt++;
            }
        }

        return cnt;
    }
}