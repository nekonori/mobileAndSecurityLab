q, public prime no.

alpha, a primitive root of q

### User A

choose Xa, private Xa < q

Ya = alpha ^ Xa mod q

### User B

choose Xb, private Xb < q

Yb = alpha ^ Xb mod q

### Common key

K = Yb ^ Xa mod q = Ya ^ Xb mod q
