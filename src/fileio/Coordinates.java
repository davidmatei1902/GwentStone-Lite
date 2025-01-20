package fileio;

public final class Coordinates {
   private int x, y;

   public Coordinates() {
   }
   public boolean isPlayerOneSpace() {
      return this.x == 3 || this.x == 2;
   }

   public boolean isPlayerTwoSpace() {
      return this.x == 1 || this.x == 0;
   }

   public int getX() {
      return x;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(final int y) {
      this.y = y;
   }

   @Override
   public String toString() {
      return "Coordinates{"
              + "x="
              + x
              + ", y="
              + y
              + '}';
   }
}
