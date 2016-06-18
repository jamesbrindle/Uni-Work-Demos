/* Example 1: TCP sockets server*/

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/param.h>
#include <netdb.h>
#include <netinet/in.h>
#include <string.h>

#define PORTNUM 1503

int main()
{

  int r_sd,			/* rendevouz socket      */ 

  c_sd;				/* communications socket (separate for each */
				/* connection) */
  char buffer[256];		/* buffer to store data  */

  struct sockaddr_in name;	/* socket name structure */
  struct hostent *me;
  char myname[MAXHOSTNAMELEN+1];

  struct in_addr *IPaddr;
  char *ptr, **pptr;

				/* create socket */
  if ((r_sd = socket( AF_INET, SOCK_STREAM, 0)) < 0) {
	printf("Cannot create rendevouz socket\n");
	return(-1);
  } 
  printf("Rendevouz socket created\n");   

  if (gethostname(myname, MAXHOSTNAMELEN) < 0) {
	printf("Cannot get my name\n"); return(-1);
  }
 
  if ((me = gethostbyname(myname)) == NULL) {
	printf("Cannot get my address\n");
	return(-1);
  }

  pptr = me->h_addr_list;
  IPaddr = (struct in_addr *) *pptr;
  
  printf( "His address is now %s\n", inet_ntoa(*IPaddr));

  printf("My name is %s\n", me->h_name);
  printf("My internet address is %s\n", inet_ntoa(*IPaddr)); 
  
  name.sin_family = AF_INET;	/* Internet domain */
  name.sin_addr.s_addr = INADDR_ANY;
  name.sin_port = PORTNUM;
				/* bind socket */
  if (bind( r_sd, (struct sockaddr *) &name, sizeof(name)) <0 ) {
	printf("Cannot create communication socket\n");
	return(-1);
  }
  printf("Communication socket created\n\n");
  listen( r_sd, 5);		/* accept client connections */
  do {
	printf("Waiting for client connections\n");
	if ((c_sd = accept( r_sd, (struct sockaddr *) 0, (int *) 0)) < 0) {
		printf("Cannot accept connection\n");
		return(-1);
	}
	read( c_sd, buffer, 256);
	printf("Received data from client:\n");
	printf( "%s\n\n", buffer);
	strcpy( buffer, "Message received.");
	write( c_sd, buffer, strlen(buffer));
	close(c_sd);
  } while (1);
  return (0); 
}
