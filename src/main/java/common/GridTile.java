package common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GridTile {
    private final int row, column;
    private final Character value;
}
