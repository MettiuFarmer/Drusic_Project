class Circle {

    int x;  // Center of the circle: X
    int y;  // Center of the circle: Y
    int r;
    float alpha;
    int yspeed = 3;  // Speed of the animation to move the circle
    int id;
    int[] colore;

    Circle(int X, int Y) {
        this.x = X;
        this.y = Y;
        this.colore = new int[3];
    }

    public Circle(){}

    // Function to move the circle up
    void up(int a) {
        this.yspeed = a;
        if (this.y > 150) {
            this.y = this.y - this.yspeed;
        }
    }

    // Function to move the circle down
    void down(int a) {
        this.yspeed = a;
        if (this.y < 450) {
            this.y = this.y + this.yspeed;
        }
    }

    // Makes the circle visible
    void show(int coloDisegno) {
        stroke(settings.getColor(coloDisegno)[0], settings.getColor(coloDisegno)[1], settings.getColor(coloDisegno)[2]);
        strokeWeight(3);
        fill(0);
        ellipse(this.x, this.y, 50, 50);
        fill(settings.getColor(coloDisegno)[0], settings.getColor(coloDisegno)[1], settings.getColor(coloDisegno)[2]);
        ellipse(this.x, this.y, 10, 10);
    }

    public void muovi(float a, float b, float c, float d) {}

    public void setColor(int a, int b, int c) {
        this.colore = new int[]{a, b, c};
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int a) {
        this.x = a;
    }

    public void setY(int a) {
        this.y = a;
    }

    void toPolar() {
        float x2 = pow(this.x, 2);
        float y2 = pow(this.y, 2);

        this.r = (int) sqrt(x2 + y2);

        this.alpha = atan(this.y / this.x);
    }

    void alpha(float f) {
        f = map(f, 0, 10, 0, 7);
        this.alpha += (f * 0.001);
    }

    float getAlpha() {
        return this.alpha;
    }

    int getR() {
        return this.r;
    }

    void setR(int r) {
        this.r = r;
    }

    void setAlpha(float a) {
        this.alpha = a;
    }

}
