package day.day8;

import common.FileReader;
import common.GridTile;

import java.util.List;
import java.util.stream.Collectors;

/*
 *  I couldn't have made this less efficient if I tried to. I hate grids and I don't understand how to manage grids.
 *  It'd be really cool if we could stop with all the grids. (╯°□°）╯︵ ┻━┻
 */
public class Day8 {

    private static final int GRID_SIZE = 99;

    public static void main(String[] args) {
        List<GridTile> grid = FileReader.readAsGrid("Day8");
        System.out.println(grid.stream().filter(gridTile -> isVisible(grid, gridTile)).count());
        System.out.println(grid.stream().map(gridTile -> sceneryScore(grid, gridTile)).min((o1, o2) -> Integer.compare(o2, o1)).get());
    }

    private static boolean isVisible(List<GridTile> grid, GridTile target) {
        if (target.getRow() == 0 || target.getColumn() == 0 || target.getRow() == GRID_SIZE - 1 || target.getColumn() == GRID_SIZE - 1)
            return true;

        var left = grid.stream().noneMatch(gridTile -> gridTile.getRow() == target.getRow() &&
                (gridTile.getColumn() < target.getColumn() && gridTile.getValue() >= target.getValue()));

        var right = grid.stream().noneMatch(gridTile -> gridTile.getRow() == target.getRow() &&
                (gridTile.getColumn() > target.getColumn() && gridTile.getValue() >= target.getValue()));

        var up = grid.stream().noneMatch(gridTile -> gridTile.getColumn() == target.getColumn() &&
                (gridTile.getRow() < target.getRow() && gridTile.getValue() >= target.getValue()));

        var down = grid.stream().noneMatch(gridTile -> gridTile.getColumn() == target.getColumn() &&
                (gridTile.getRow() > target.getRow() && gridTile.getValue() >= target.getValue()));
        return (up || right || down || left);
    }

    private static int sceneryScore(List<GridTile> grid, GridTile target) {
        var left = grid.stream().filter(gridTile -> gridTile.getRow() == target.getRow() && gridTile.getColumn() < target.getColumn()).collect(Collectors.toList());
        int leftScore = 0;
        for (int i = target.getColumn(); i > 0; i--) {
            if (left.get(i - 1).getValue() >= target.getValue()) {
                leftScore++;
                break;
            }
            leftScore++;
        }

        var right = grid.stream().filter(gridTile -> gridTile.getRow() == target.getRow() && gridTile.getColumn() > target.getColumn()).collect(Collectors.toList());
        int rightScore = 0;
        for (int i = 0; i < GRID_SIZE - target.getColumn() - 1; i++) {
            if (right.get(i).getValue() >= target.getValue()) {
                rightScore++;
                break;
            }
            rightScore++;
        }

        var up = grid.stream().filter(gridTile -> gridTile.getColumn() == target.getColumn() && gridTile.getRow() < target.getRow()).collect(Collectors.toList());
        int upScore = 0;
        for (int i = target.getRow(); i > 0; i--) {
            if (up.get(i - 1).getValue() >= target.getValue()) {
                upScore++;
                break;
            }
            upScore++;
        }

        var down = grid.stream().filter(gridTile -> gridTile.getColumn() == target.getColumn() && gridTile.getRow() > target.getRow()).collect(Collectors.toList());
        int downScore = 0;
        for (int i = 0; i < GRID_SIZE - target.getRow() - 1; i++) {
            if (down.get(i).getValue() >= target.getValue()) {
                downScore++;
                break;
            }
            downScore++;
        }
        return leftScore * rightScore * upScore * downScore;
    }
}
