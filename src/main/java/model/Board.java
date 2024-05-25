    package model;

    import java.io.Serializable;
    import java.util.Random;

    public class Board implements Serializable {
        public Tile[][] board;
        private boolean spawned;

        public Board(int size) {
            board = new Tile[size][size];
        }

        /**
         * Generates Coordinates dependent on boardsize, checks if the slot for the coordinates is empty, if no it repeats
         */
        public void spawn() {
            if (checkSpace() > 0) {
                Random rnd = new Random();
                Tile t3 = new Tile(board.length);
                int boardWidth = board.length;
                int boardHeight = board.length;

                do {
                    t3.setX(rnd.nextInt(boardWidth));
                    t3.setY(rnd.nextInt(boardHeight));
                } while (board[t3.getY()][t3.getX()] != null);

                board[t3.getY()][t3.getX()] = t3;

                spawned= true;
            } else {
                spawned= false;
            }
        }

        /**
         *
         * @return highest Number
         */
        public int getHighestNumber() {
            int highest = 0;
            for(int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != null) {
                        if (highest < board[i][j].getNumber()) {
                            highest = board[i][j].getNumber();
                        }
                    }
                }
            }
            return highest;
        }

        public boolean isSpawned() {
            return spawned;
        }

        /**
         *
         * @return the amount of empty slots in the board
         */
        public int checkSpace() {
            int count = 0;
            for (int col = 0; col < board.length; col++) {
                for (int row = 0; row < board[col].length; row++) {
                    if (board[col][row] == null) {
                        count++;
                    }
                }
            }
            return count;
        }

        public int getBoardSize() {
            return board.length;
        }


        /**
         * Goes through each Tile in the board and moves it until it collides with something, if its the border of the game Board its finished, if its another Tile
         * it checks the Numbers and if they have the same Number they merge into one
         */
        public void moveUp() {
            boolean moved = false;
            do {
                moved = false;
                for (int row = 0; row < board[0].length; row++) {
                    for (int col = 1; col < board.length; col++) {
                        if (board[col][row] != null) {
                            int currentCol = col;
                            while (board[currentCol][row] != null && currentCol > 0 && (board[currentCol - 1][row] == null ||
                                    board[currentCol - 1][row].getNumber() == board[currentCol][row].getNumber())) {

                                if (board[currentCol][row] != null && board[currentCol - 1][row] == null) {
                                    board[currentCol - 1][row] = board[currentCol][row];
                                    board[currentCol][row] = null;
                                    board[currentCol - 1][row].setY(currentCol - 1);
                                    moved = true;
                                } else if (board[currentCol][row] != null && board[currentCol - 1][row].getNumber() == board[currentCol][row].getNumber()) {
                                    board[currentCol - 1][row].setNumber(board[currentCol - 1][row].getNumber() * 2);
                                    board[currentCol][row] = null;
                                    moved = true;
                                }
                            }
                        }
                    }
                }
            } while (moved);
            spawn();
        }

        /**
         * Goes through each Tile in the board and moves it until it collides with something, if its the border of the game Board its finished, if its another Tile
         * it checks the Numbers and if they have the same Number they merge into one
         */
        public void moveDown() {
            boolean moved = false;
            do {
                moved = false;
                for (int row = 0; row < board[0].length; row++) {
                    for (int col = board.length - 2; col >= 0; col--) {
                        if (board[col][row] != null) {
                            int currentCol = col;
                            while (board[currentCol][row] != null && currentCol < board.length - 1 && (board[currentCol + 1][row] == null ||
                                    board[currentCol + 1][row].getNumber() == board[currentCol][row].getNumber())) {
                                if (board[currentCol + 1][row] == null && board[currentCol][row] != null) {
                                    board[currentCol + 1][row] = board[currentCol][row];
                                    board[currentCol][row] = null;

                                    board[currentCol + 1][row].setY(currentCol + 1);
                                    moved = true;
                                } else if (board[currentCol][row] != null && board[currentCol + 1][row].getNumber() == board[currentCol][row].getNumber()) {
                                    board[currentCol + 1][row].setNumber(board[currentCol + 1][row].getNumber() * 2);
                                    board[currentCol][row] = null;
                                    moved = true;
                                }
                            }
                        }
                    }
                }
            } while (moved);
            spawn();
        }

        /**
         * Goes through each Tile in the board and moves it until it collides with something, if its the border of the game Board its finished, if its another Tile
         * it checks the Numbers and if they have the same Number they merge into one
         */
        public void moveLeft() {
            boolean moved = false;
            do {
                moved = false;
                for (int col = 0; col < board.length; col++) {
                    for (int row = 1; row < board[0].length; row++) {
                        if (board[col][row] != null) {
                            int currentRow = row;
                            while (board[col][currentRow] != null && currentRow > 0 && (board[col][currentRow - 1] == null ||
                                    board[col][currentRow - 1].getNumber() == board[col][currentRow].getNumber())) {
                                if (board[col][currentRow - 1] == null && board[col][currentRow] != null) {
                                    board[col][currentRow - 1] = board[col][currentRow];
                                    board[col][currentRow] = null;

                                    board[col][currentRow - 1].setX(currentRow - 1);
                                    moved = true;
                                } else if (board[col][currentRow] != null && board[col][currentRow - 1].getNumber() == board[col][currentRow].getNumber()) {
                                    board[col][currentRow - 1].setNumber(board[col][currentRow - 1].getNumber() * 2);
                                    board[col][currentRow] = null;
                                    moved = true;
                                }
                            }
                        }
                    }
                }
            } while (moved);
            spawn();
        }

        /**
         * Goes through each Tile in the board and moves it until it collides with something, if its the border of the game Board its finished, if its another Tile
         * it checks the Numbers and if they have the same Number they merge into one
         */
        public void moveRight() {
            boolean moved = false;
            do {
                moved = false;
                for (int col = 0; col < board.length; col++) {
                    for (int row = board[0].length - 2; row >= 0; row--) {
                        if (board[col][row] != null) {
                            int currentRow = row;
                            while (board[col][currentRow] != null && currentRow < board[0].length - 1 && (board[col][currentRow + 1] == null ||
                                    board[col][currentRow + 1].getNumber() == board[col][currentRow].getNumber())) {
                                if (board[col][currentRow + 1] == null && board[col][currentRow] != null) {
                                    board[col][currentRow + 1] = board[col][currentRow];
                                    board[col][currentRow] = null;

                                    board[col][currentRow + 1].setX(currentRow + 1);
                                    moved = true;
                                } else if (board[col][currentRow] != null && board[col][currentRow + 1].getNumber() == board[col][currentRow].getNumber()) {
                                    board[col][currentRow + 1].setNumber(board[col][currentRow + 1].getNumber() * 2);
                                    board[col][currentRow] = null;
                                    moved = true;
                                }
                            }
                        }
                    }
                }
            } while (moved);
            spawn();
        }

        /**
         * Empty the board, which sets every value to NULL
         */
        public void clearBoard() {
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[0].length; j++) {
                    board[i][j] = null;
                }
            }
        }


        /**
         *
         * @param y - Y coordinate
         * @param x - X coordinate
         * @return Tile from the coordinates
         */
        public Tile getTile(int y, int x) {
            return board[y][x];
        }
    }