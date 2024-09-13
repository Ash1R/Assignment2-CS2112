/**
 * A rational number p/q for integers p and q
 */
public class Rational {

    /**
     * Numerator of this rational number.
     * <p>
     * INVARIANT: gcd(n, d) = 1
     */
    private int n;

    /**
     * Denominator of this rational number.
     * <p>
     * INVARIANT: d > 0, gcd(n, d) = 1
     */
    private int d;

    /**
     * A rational number num/den
     *
     * @param num Numerator
     * @param den Denominator. Must not be 0.
     */
    public Rational(int num, int den) {
        if (den < 0) {
            num = -num;
            den = -den;
        }
        int g = gcd(num, den);
        n = num / g;
        d = den / g;
    }

    /**
     * Update this to be +r.
     *
     * @param r Other number to add. Not modified.
     */
    public void add(Rational r) {
        int g = gcd(d, r.d);
        n = (r.d * n + d * r.n) / g;
        d = d * r.d / g;
    }

    /**
     * Update this rational number to be 1/this.
     * <p>
     * TODO: write more docs here
     */
    public void reciprocal() throws ArithmeticException{
        if (n == 0){
            throw new ArithmeticException;
        }
        int temp = this,n;
        this.n = this.d;
        this.d = temp;

        if (d < 0) {
            this.n = -this.n;
            this.d = -this.d;
        }
    }

    /**
     * Add two rational numbers, x+y.
     * <p>
     * Does not modify any of its inputs.
     *
     * @param x First term
     * @param y Second term
     * @return A new rational number that is equal to x+y
     */
    public static Rational plus(Rational x, Rational y) {
        Rational z = new Rational(x.n, x.d);
        z.add(y);
        return z;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Rational r)) {
            return false;
        }
        return (n == r.n && d == r.d);
    }

    public String toString() {
        return n + "/" + d;
    }

    /**
     * The GCD of two integers.
     *
     * @return The GCD, always non-negative
     */
    private int gcd(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }
}