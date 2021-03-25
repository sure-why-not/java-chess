package chess.domain.location;

import java.util.Objects;

public class Location {

    private static final int MIN_LOCATION = 1;
    private static final int MAX_LOCATION = 8;

    private final int x;
    private final int y;

    private Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Location of(final int x, final int y) {
        validateRange(x, y);
        return new Location(x, y);
    }

    public static Location of(String location) {
        if (location.length() != 2) {
            throw new IllegalArgumentException("[ERROR] 좌표는 x와 y값 두개로 이루어져야 합니다.");
        }
        int xPos = location.charAt(0) - 'a' + 1;
        int yPos = Character.digit(location.charAt(1), 10);
        validateRange(xPos, yPos);
        return new Location(xPos, yPos);
    }

    private static void validateRange(final int x, final int y) {
        if (!isRange(x, y)) {
            throw new IllegalArgumentException("범위에서 벗어났습니다.");
        }
    }

    private static boolean isRange(int x, int y) {
        return (MIN_LOCATION <= x && x <= MAX_LOCATION)
            && (MIN_LOCATION <= y && y <= MAX_LOCATION);
    }

    public boolean canMoveHorizontallyOrVerticallyTo(final Location target) {
        return canMoveHorizontallyTo(target) || canMoveVerticallyTo(target);
    }

    private boolean canMoveHorizontallyTo(final Location target) {
        return this.y == target.y;
    }

    private boolean canMoveVerticallyTo(final Location target) {
        return this.x == target.x;
    }

    public boolean canMoveDigonallyTo(final Location target) {
        return Math.abs(this.x - target.x) == Math.abs(this.y - target.y);
    }

    public boolean isAdjacent(final Location target) {
        return Math.abs(this.x - target.x) <= 1 && Math.abs(this.y - target.y) <= 1;
    }

    public int subtractX(final Location target) {
        return target.x - this.x;
    }

    public int subtractY(final Location target) {
        return target.y - this.y;
    }

    public boolean isSameY(final int y) {
        return this.y == y;
    }

    public Location moveByStep(int dx, int dy) {
        return Location.of(x + dx, y + dy);
    }

    public boolean isRangeByStep(int dx, int dy) {
        return isRange(x + dx, y + dy);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}