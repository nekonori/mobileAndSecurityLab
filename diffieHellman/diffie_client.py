import socket

def prime_checker(p):
    # Checks If the number entered is a Prime Number or not
    if p < 1:
        return -1
    elif p > 1:
        if p == 2:
            return 1
        for i in range(2, p):
            if p % i == 0:
                return -1
            return 1
 
 
def primitive_check(g, p, L):
    # Checks If The Entered Number Is A Primitive Root Or Not
    for i in range(1, p):
        L.append(pow(g, i) % p)
    for i in range(1, p):
        if L.count(i) > 1:
            L.clear()
            return -1
        return 1

def get_string_from_bytes(bin_string):
    # print("got", bin_string)
    return (str(bin_string))[2:-1]

def main():
    host = socket.gethostname()
    port = 5322
    client_socket = socket.socket()
    client_socket.connect((host, port))
    l = []
    while 1:
        P = int(input("Enter P : "))
        if prime_checker(P) == -1:
            print("Number Is Not Prime, Please Enter Again!")
            continue
        break
    client_socket.send(bytes(str(P), 'utf-8'))
    while 1:
        G = int(input(f"Enter The Primitive Root Of {P} : "))
        if primitive_check(G, P, l) == -1:
            print(f"Number Is Not A Primitive Root Of {P}, Please Try Again!")
            continue
        break
    
    client_socket.send(bytes(str(G), 'utf-8'))

    x1 = int(input("enter private key of client:"))
    client_socket.send(bytes('received x1', 'utf-8'))


    data = client_socket.recv(1024)
    y2 = int(get_string_from_bytes(data))

    print("y2 was received from server")
    
    # Private Keys
    # x1, x2 = int(input("Enter The Private Key Of User 1 : ")), int(
    #     input("Enter The Private Key Of User 2 : "))
    # while 1:
    #     if x1 >= P or x2 >= P:
    #         print(f"Private Key Of Both The Users Should Be Less Than {P}!")
    #         continue
    #     break
    
    # Calculate Public Keys
    # y1, y2 = pow(G, x1) % P, pow(G, x2) % P
    y1 = pow(G, x1)
    print("Sending y1 to server...")
    client_socket.send(bytes(str(y1), 'utf-8'))
    
    # Generate Secret Keys
    # k1, k2 = pow(y2, x1) % P, pow(y1, x2) % P
    k1 = pow(y2, x1) % P
    print("Shared key", k1)
    client_socket.close()
    
    # print(f"\nSecret Key For User 1 Is {k1}\nSecret Key For User 2 Is {k2}\n")
    
    # if k1 == k2:
    #     print("Keys Have Been Exchanged Successfully")
    # else:
    #     print("Keys Have Not Been Exchanged Successfully")

if __name__ == "__main__":
	main()