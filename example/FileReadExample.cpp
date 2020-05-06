#include <stdio.h>
#include <stdlib.h>
int main(void) {

   int num;
   FILE *fptr;

   if ((fptr = fopen("test.txt","r")) == NULL) {       // checks if file exists
       puts("File not exists");
       exit(1);                    // for exit(1) is required #include <stdlib.h>
   } else

   fscanf(fptr,"%d", &num);

   printf("My pass is:  %d\n", num);
   fclose(fptr);

   return 0;

}