#include <iostream>
#include <string>
#include <stdio.h>
#include <curl/curl.h>

#include <boost/algorithm/string.hpp>
using namespace std;
int main() {
vector<string> result;
       string s1("*   Trying ::1...");
       string s2("\n* TCP_NODELAY set");
       string s3("\n* Connected to localhost (::1) port 8080 (#0)");
       string s4("\n> GET /socket/ HTTP/1.1");
       string s5("\nHost: localhost:8080");
       string s6("\nAccept: */*");
       string s7("\n");
       string s8("\n< HTTP/1.1 200");
       string s9("\n< Content-Type: text/plain;charset=UTF-8");
       string s10("\n< Content-Length: 12");
       string s11("\n< Date: Tue, 05 May 2020 08:46:57 GMT");
       string s12("\n<");
       string s13("\n* Connection #0 to host localhost left intact");
       string s14("\n10");

   string payload  = s1+s2+s3+s4+s5+s6+s7+s8+s9+s10+s11+s12+s13+s14;

int res;
   try
     {

      boost::split(result, payload, boost::is_any_of("\n"));

          int size = static_cast<int>(result.size());//14
          string httpStatus = "HTTP/1.1 200";
          bool stringContains = boost::algorithm::contains(payload, httpStatus);// 1==true
            cout << stringContains << endl;

          //printf("vector size : %d\n", size);
          bool vectorSize = size == 14;


            if (vectorSize){
              res = stoi(result[13]);
               cout <<"Return server value : "<< res<< endl;
              }
             //cout<<payload<<endl;

             //for (int i = 0; i < result.size(); i++)
               //      cout << result[i] << endl;
     }
     catch (const char* msg) {
       cerr << msg << endl;
     }



}



