#include <stdio.h>
#include <string.h>
#include <netinet/in.h> 
#include <errno.h>
#include <stdlib.h>

int findStr (char *buf);

int errexit(char *format, ...);

#define QLEN   5

/*------------------------------------------------------------------------ */
/* main - Concurrent TCP server for DICTionary application                 */
/*------------------------------------------------------------------------ */
int main (argc, argv)
int   argc;
char *argv[];
{
  struct  sockaddr_in fsin;	/* the from address of a client  */
  char   *service = "5000";	/* service name or port number   */
  int   msock, ssock;		/* master & slave sockets   */
  int   alen;			/* from-address length      */

  int   status;
  char  buf[80];
  int   flag= 1;

  switch (argc) {
  case   1:
	break;
  case   2:
	service = argv[1];
	break;
  default:
	errexit ("Usage: %s [port]\n", argv[0]);
  }

				/* create passive socket  */
  msock= passiveTCP (service, QLEN);
  fprintf (stderr, "Dictionary service on port %s started!\n", service);

  while (flag == 1) {
				/* accept connection from client */
	alen = sizeof(fsin);
	ssock= accept (msock, (struct sockaddr *)&fsin, &alen);

	if (ssock < 0)
		errexit ("accept error %s\n", strerror(errno));

	fprintf (stderr, "Connection from: %s\n\r", inet_ntoa (fsin.sin_addr));

	if (fork() == 0) {
				/* Do the work with the client */
		flag= TCPdict (ssock);
				/* Close connection to client */
		close (ssock);
		exit(0);
	}
	close (ssock);
  }
}

/*------------------------------------------------------------------------ */
/* TCPdict - do DICTionary search                                          */
/*------------------------------------------------------------------------ */
int TCPdict (fd)
int   fd;
{
  int   ret;
  char  buffer[80];

  sprintf (buffer, "Internet Dictionary service\n");
				/* send header string */
  write (fd, buffer, strlen (buffer));

				/* read the word to query for */
  ret = read (fd, buffer, sizeof (buffer));
  buffer [ret] = 0;
				/* Find the result */
  ret= findStr (buffer);
   
				/* Send the result */
  write (fd, buffer, strlen (buffer)) ;

  return ret;
}

/*------------------------------------------------------------------------ */
/*   searches for specified word and returns the result in the same buffer */
/*------------------------------------------------------------------------ */
int findStr (char *buf)
{
  FILE * f;
  char str[80], *str1;		/* Do not change the length of the str */
  int i, flag= 0;
   
  if (strcmp (buf, "Down")==0)
	return 0;
     
  f= fopen ( "dict.dat","r");
   
  while (!feof (f)) {
	fgets  (str, 79, f);
	str1= index (str, '-');

	if (str1==NULL)
		continue;
     
	*(str1++)= 0;
	if (strcmp (buf, str) == 0) {
		flag= 1;
		strcpy (buf, str1);
		break;
	}
  }
    
  if (flag==0) {
	strcpy (buf, "(sorry: no entries)");
  }
    
  close(f);
  return 1;
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

