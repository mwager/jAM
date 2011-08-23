package abc.xml;

public class MathUtils {

	public static long ppcm(int a, int b) {
        return Math.abs(multiplyAndCheck(a / pgcd(a, b), b));
}

public static int pgcd(int u, int v) {
    if (u * v == 0) {
        return (Math.abs(u) + Math.abs(v));
    }
    // keep u and v negative, as negative integers range down to
    // -2^31, while positive numbers can only be as large as 2^31-1
    // (i.e. we can't necessarily negate a negative number without
    // overflow)
    /* assert u!=0 && v!=0; */
    if (u > 0) {
        u = -u;
    } // make u negative
    if (v > 0) {
        v = -v;
    } // make v negative
    // B1. [Find power of 2]
    int k = 0;
    while ((u & 1) == 0 && (v & 1) == 0 && k < 31) { // while u and v are
                                                        // both even...
        u /= 2;
        v /= 2;
        k++; // cast out twos.
    }
    if (k == 31) {
        throw new ArithmeticException("overflow: gcd is 2^31");
    }
    // B2. Initialize: u and v have been divided by 2^k and at least
    // one is odd.
    int t = ((u & 1) == 1) ? v : -(u / 2)/* B3 */;
    // t negative: u was odd, v may be even (t replaces v)
    // t positive: u was even, v is odd (t replaces u)
    do {
        /* assert u<0 && v<0; */
        // B4/B3: cast out twos from t.
        while ((t & 1) == 0) { // while t is even..
            t /= 2; // cast out twos
        }
        // B5 [reset max(u,v)]
        if (t > 0) {
            u = -t;
        } else {
            v = t;
        }
        // B6/B3. at this point both u and v should be odd.
        t = (v - u) / 2;
        // |u| larger: t positive (replace u)
        // |v| larger: t negative (replace v)
    } while (t != 0);
    return -u * (1 << k); // gcd is u*2^k
}


public static long multiplyAndCheck(long a, long b) {
    long ret;
    String msg = "overflow: multiply";
    if (a > b) {
        // use symmetry to reduce boundry cases
        ret = multiplyAndCheck(b, a);
    } else {
        if (a < 0) {
            if (b < 0) {
                // check for positive overflow with negative a, negative b
                if (a >= Long.MAX_VALUE / b) {
                    ret = a * b;
                } else {
                    throw new ArithmeticException(msg);
                }
            } else if (b > 0) {
                // check for negative overflow with negative a, positive b
                if (Long.MIN_VALUE / b <= a) {
                    ret = a * b;
                } else {
                    throw new ArithmeticException(msg);
                    
                }
            } else {
                // assert b == 0
                ret = 0;
            }
        } else if (a > 0) {
            // assert a > 0
            // assert b > 0
            
            // check for positive overflow with positive a, positive b
            if (a <= Long.MAX_VALUE / b) {
                ret = a * b;
            } else {
                throw new ArithmeticException(msg);
            }
        } else {
            // assert a == 0
            ret = 0;
        }
    }
    return ret;
}

}
