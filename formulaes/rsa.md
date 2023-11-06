p, q - private, 2 chosen prime nos
n = p \* q, public

phi(n) = (p-1)\*(q-1)

choose e such that gcd(e, phi(n)) = 1 and 1 < e < phi(n)

d = e^-1 mod(phi(n))

c = M^e mod n

M = C^d mod n
