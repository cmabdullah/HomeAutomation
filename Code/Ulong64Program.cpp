#include <iostream>
#include<time.h>
using namespace std;
typedef unsigned long long ulong64;
//https://raspberrypi.stackexchange.com/questions/8808/zero-crossing-activated-relay
//https://github.com/OP-TEE/optee_os/blob/master/core/lib/libtomcrypt/src/prngs/fortuna.c
//https://stackoverflow.com/questions/2844/how-do-you-format-an-unsigned-long-long-int-using-printf
//https://lore.kernel.org/lkml/20190605144116.28553-3-alexander.sverdlin@nokia.com/T/
//https://stackoverflow.com/questions/4160945/long-long-int-vs-long-int-vs-int64-t-in-c
//https://stackoverflow.com/questions/384502/what-is-the-bit-size-of-long-on-64-bit-windows
//https://docs.microsoft.com/en-us/windows/win32/winprog/windows-data-types?redirectedfrom=MSDN
// overflow https://docs.microsoft.com/en-us/cpp/error-messages/compiler-warnings/compiler-warning-level-4-c4754?view=vs-2019

ulong64 timer(unsigned char reset){//ulong64 timer()
   struct timespec t;
   static struct timespec lt={0,0};
   clock_gettime(CLOCK_REALTIME, &t);
   //CLOCK_REALTIME System-wide realtime clock. Setting this clock requires appropriate privileges.

   if(reset){//cm -> if reset = 0 then condition is false
      lt.tv_sec = t.tv_sec;
      lt.tv_nsec = t.tv_nsec;
   }

   printf("t.tv_nsec : %ld  \n", t.tv_nsec);
   printf("lt.tv_nsec : %ld \n", lt.tv_nsec);
   //int r = ((ulong64)(t.tv_sec - lt.tv_sec))*1000 + (t.tv_nsec - lt.tv_nsec)/1000000;
   ulong64 r = ((ulong64)(t.tv_sec - lt.tv_sec))*1000 + (t.tv_nsec - lt.tv_nsec)/1000000;
   /// get microseconds, get nanoseconds

   //printf("r %d ", r);
   printf("r %llu \n", r);
   return r;
}

int main() {
   //std::cout << sizeof(long)*8 << std::endl;
   // unsigned long long x = timer(240);
   //Calling timer(1) resets it, calling timer(0) returns time since reset.
   ulong64 x = timer(0);//max 255 if put 0 res correct else 0 dont know why
   //Calling timer(1) resets it,
   //calling timer(0) returns time since reset.
   printf("result %llu \n", x);
   //printf("%lld", x);
   //ulong64 -> unsigned long long int
}

/***
-127 to 128 sign
0-255 un sign
I'm using a timer function in milliseconds (that's about the precision I need) based on clock_gettime() to clock my delays.

Calling timer(1) resets it, calling timer(0) returns time since reset.
An unsigned char is a (unsigned) byte value (0 to 255). You may be thinking of "char" in terms of being a "character" but it is really a numerical value.

ULONG64 An unsigned LONG64. The range is 0 through 18446744073709551615 decimal.
struct timespec {
        time_t   tv_sec;        // seconds
        long     tv_nsec;       // nanoseconds
};


Type                        | S/U | x86    | x64
----------------------------+-----+--------+-------
BYTE, BOOLEAN               | U   | 8 bit  | 8 bit
----------------------------+-----+--------+-------
SHORT                       | S   | 16 bit | 16 bit
USHORT, WORD                | U   | 16 bit | 16 bit
----------------------------+-----+--------+-------
INT, LONG                   | S   | 32 bit | 32 bit
UINT, ULONG, DWORD          | U   | 32 bit | 32 bit
----------------------------+-----+--------+-------
INT_PTR, LONG_PTR, LPARAM   | S   | 32 bit | 64 bit
UINT_PTR, ULONG_PTR, WPARAM | U   | 32 bit | 64 bit
----------------------------+-----+--------+-------
LONGLONG                    | S   | 64 bit | 64 bit
ULONGLONG, QWORD            | U   | 64 bit | 64 bit

g++ -o a.out Ulong64Program.cpp && ./a.out

*/