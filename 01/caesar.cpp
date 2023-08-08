#include <bits/stdc++.h>

using namespace std;

string encrypt(string word, int key){
    string s = "";
    for(int i=0;i<word.size();i++){
        char c = word[i];
        int j = c - 'a';
        c = (j+key) % 26 + 97;
        s += c;
    }
    return s;
}

string decrypt(string word, int key){
    string s = "";
    for(int i=0;i<word.size();i++){
        char c = word[i];
        int j = c - 'a';
        if(j-key<0){
            c = ((j-key)+26)%26+97;
        } else {
            c = (j-key)%26+97;
        }
        s += c;
    }
    return s;
}

int main()
{
    string word;
    cout<<"Enter the word: ";
    cin>>word;
    int key;
    cout<<"Enter key: ";
    cin>>key;
    string e, d;
    e = encrypt(word, key);
    d = decrypt(e, key);
    cout<<"Original string: "<<word<<endl;
    cout<<"Encrypted string: "<<e<<endl;
    cout<<"Decrypted string: "<<d<<endl;
    return 0;
}
