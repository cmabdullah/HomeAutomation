#include <iostream>
#include <string>
#include <curl/curl.h>
#include <boost/algorithm/string.hpp>
#include <string>
#include <stdio.h>
using namespace std;
//https://stackoverflow.com/questions/2329571/c-libcurl-get-output-into-a-string
//https://stackoverflow.com/questions/2329571/c-libcurl-get-output-into-a-string
size_t CurlWrite_CallbackFunc_StdString(void *contents, size_t size, size_t nmemb, string *s)
{
    size_t newLength = size*nmemb;
    try
    {
        s->append((char*)contents, newLength);

    }
    catch(bad_alloc &e)
    {
        //handle memory problem
        return 0;
    }
    return newLength;
}
int main()
{
    CURL *curl;
    CURLcode res;

    curl_global_init(CURL_GLOBAL_DEFAULT);

    curl = curl_easy_init();
    string s;
    if(curl)
    {

        curl_easy_setopt(curl, CURLOPT_URL, "http://localhost:8080/socket/");

        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L); //only for https
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L); //only for https
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, CurlWrite_CallbackFunc_StdString);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &s);
        curl_easy_setopt (curl, CURLOPT_VERBOSE, 1L); //remove this to disable verbose output


        /* Perform the request, res will get the return code */
        res = curl_easy_perform(curl);
        /* Check for errors */
        if(res != CURLE_OK)
        {
            fprintf(stderr, "curl_easy_perform() failed: %s\n",
                    curl_easy_strerror(res));
        }

        /* always cleanup */
        curl_easy_cleanup(curl);
    }

    //cout<<"result is : "<< s<<endl;





    try{

    int convertToInt = stoi(s);

    cout<<"result is : "<< convertToInt <<endl;

    }catch(invalid_argument const &e){
        cout<< "Bad input: std:invalid_argument thrown"<< endl;
    }catch(out_of_range const &e){
        cout<< "Invalid overflow: std::out_of_range  thrown"<< endl;
    }




    //cout<< "Program finished!" << endl;
}

