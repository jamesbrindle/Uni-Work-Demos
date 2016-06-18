#include <stdio.h>
#include <string.h>
#include <stdlib.h>

extern int   errno;
extern const char *const sys_errlist[];

/*------------------------------------------------------------ */
/* main - TCP client for DICTIONARY service                    */
/*------------------------------------------------------------ */
int main (argc, argv)
int   argc;
char *argv[];
{
  char   *host = "localhost";   /* host to use if none supplied   */
  char   *service = "5000";     /* default service port      */
  char   *word;
   
  switch (argc) {
  case 2:
	host= "localhost";
	word= argv[1];
	break;
  case 4:
	service= argv[3];
				/* FALL THROUGH */
  case 3:
	host = argv[2];
	word = argv[1];
	break;
  default:
	fprintf (stderr, "Usage: %s word [host [port]]\n", argv[0]);
	exit(1);
  }

  TCPdict (word, host, service);
  exit(0);
}

/*------------------------------------------------------------ */
/* Search for 'word' approaching 'host' through port 'service' */
/*------------------------------------------------------------ */

TCPdict (word, host, service)
char   *word;
char   *host;
char   *service;
{
  char   buf[255];
  int   s, n;			/* socket, read count

				/* connect to specified host and port  */
  s= connectTCP (host, service);
				/* read the header message from server  */
  n= read  (s, buf, 254);
  buf[n]= 0;
  printf ("Connected to: %s\n%s\n", host, buf);

				/* send the word we seeking information for */
  write (s, word, strlen (word));
				/* read the result of query */
  n= read (s, buf, 254);
  buf[n]= 0;
  printf ("%s- %s\n", word, buf);
}

#include <stdarg.h>

/*------------------------------------------------------------------------ */
/*  errexit - print an error message and exit                              */
/*------------------------------------------------------------------------ */

int errexit(char *format, ...)
{
    va_list args;
    char *s1;
    char *s2;
    char *s3;
    int  n;
			              
    va_start (args, *format);
    vfprintf (stderr, format, args);
    va_end (args);
    exit (1);
}

