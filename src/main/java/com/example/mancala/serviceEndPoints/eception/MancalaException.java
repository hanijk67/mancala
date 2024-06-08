package com.example.mancala.serviceEndPoints.eception;

public class MancalaException {

    public static class CellIsEmptyException extends RuntimeException {
        public CellIsEmptyException(String message, Exception ex) {
            super(message, ex);
        }

        public CellIsEmptyException(String message) {
            super(message);
        }

        public CellIsEmptyException(Exception ex) {
            super(ex);
        }
        public CellIsEmptyException() {
        }
    }

    public static class CellIsNotValidException extends RuntimeException {
        public CellIsNotValidException(String message, Exception ex) {
            super(message, ex);
        }

        public CellIsNotValidException(String message) {
            super(message);
        }

        public CellIsNotValidException(Exception ex) {
            super(ex);
        }
        public CellIsNotValidException() {
        }
    }
    public static class PurgeFromOppositeException extends RuntimeException {
        public PurgeFromOppositeException(String message, Exception ex) {
            super(message, ex);
        }

        public PurgeFromOppositeException(String message) {
            super(message);
        }

        public PurgeFromOppositeException(Exception ex) {
            super(ex);
        }
        public PurgeFromOppositeException() {
        }
    }
    public static class GameNotStartedException extends RuntimeException {
        public GameNotStartedException(String message, Exception ex) {
            super(message, ex);
        }

        public GameNotStartedException(String message) {
            super(message);
        }

        public GameNotStartedException(Exception ex) {
            super(ex);
        }
        public GameNotStartedException() {
        }
    }
    public static class PlayerNumberIsNotValidException extends RuntimeException {
        public PlayerNumberIsNotValidException(String message, Exception ex) {
            super(message, ex);
        }

        public PlayerNumberIsNotValidException(String message) {
            super(message);
        }

        public PlayerNumberIsNotValidException(Exception ex) {
            super(ex);
        }
        public PlayerNumberIsNotValidException() {
        }
    }
}
