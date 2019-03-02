// ExecLocal.cpp
// Batch Processor for Generating and Testing Graphs
// No file name is passed in.
// The file name will be created according to the last 4 command line arguments

//#include <process.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
//#include <wait.h>
#include <string.h>
#include <iostream>

using namespace std;

#define SEED_ARG 6     // argument number of seed for GenGraph (starting from 0)

int main(int argc,char **argv)
{int termstat,pid,count;

// Command line arguments are:
//              <executable name> <#graphs> |V| <degree> <max cost> <seed>

 char **CommandArgs,thisSeed[5];

 int RandSeed=atoi(argv[SEED_ARG]); // Initial Seed from user
 cout << "Rand Seed (argv[SEED_ARG]) is " << RandSeed << endl;
 // argv[1] is # executions; store in local variable
 int numTimes=atoi(argv[2]);
 for (count=0;count<numTimes;count++) {
 /*
  if ((pid=fork())<0) {
        perror("fork");
        exit(-1);
  }
  
  else if (!pid) { // child process
*/   sprintf(thisSeed,"%2d",(RandSeed+count*7)%100);
   // New seed for creating the graph
   strcpy(argv[SEED_ARG],thisSeed);
   // Create the file name; it will go in argv[2], where the #graphs was
   //   (we stored that elsewhere)
   argv[2]=new char[40];  // must reinitialize so as not to contaminate neighbors
   sprintf(argv[2],"%s.%s.%s.%s",argv[3],argv[4],argv[5],thisSeed);
   if (argc>2) {  // print the command string upcoming
        for (int j=0;j<argc;j++)
                cout << argv[j] << " ";
        cout << endl;
    // Visual C++ way of doing it....
    pid=_spawnv(_P_WAIT,argv[2],argv+2);
    _cwait( &termstat, pid, _WAIT_CHILD );
    // Unix way....
    // pid=execv(argv[1],argv+1);

   }
   else { 
    pid=_spawnl(_P_WAIT,"debug\\Local.exe","debug\\Local.exe",
          "1","sga.out","10","1000","0.01","0","1","9","25",NULL);
    _cwait( &termstat, pid, _WAIT_CHILD );
    cout << "Return code" << termstat << endl;
    
   }
//  } // else if // child process
/*  else // parent just waits
        wait(&termstat);
*/	
 } // for loop
 return(0);
} // main

