/*  TCP Sockets     Client site   */

#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <string.h>

#define PORTNUM 1503

int main()
{ char hname[20], data[256];	/* strings to store host name and message to */
				/* be transmitted   */
  int sd, len;			/* client socket descriptor */ 
  struct sockaddr_in servername;	/* server socket name       */
  struct hostent *host;		/* server host structure    */
   
  struct in_addr *IPaddr;
  char *ptr, **pptr;
  if ((sd = socket( AF_INET, SOCK_STREAM, 0)) < 0) {
				/* create client socket */
	printf("Cannot create client socket\n");
	return(-1);
  } 
  printf("Client socket created\n");   
  printf("Enter host name: ");
  scanf("%s", hname);
  getchar();

				/* Obtain network address of server */
  if ((host = gethostbyname(hname)) == 0) {
	printf("Cannot get host by name\n");
	return (-1);
  }

  pptr = host->h_addr_list;
  IPaddr = (struct in_addr *) *pptr;
  
  printf( "Host %s got by its name\n", hname); 
  printf( "His address is %s\n", inet_ntoa(*IPaddr));


				/* initialize server socket address */
  bcopy( host->h_addr, (char *) &servername.sin_addr, host->h_length);

  servername.sin_family = AF_INET;
  servername.sin_port = PORTNUM;
				/* attempt to connect to server */
  if (connect( sd, (struct sockaddr *) &servername, sizeof(servername))<0) {
	printf("Connection refused\n");
	return (-1);
  }

  printf("Enter data to send: ");
  fgets(data, 60, stdin);
   
				/* write data to socket; close socket */
  printf("Writing data to socket...\n");
int x;
x = strlen(data) ;

  write( sd, data, x +1);


  printf("Waiting for response...\n");*data = 0;
  while ((len = read( sd, data, 255)) == 0);
  *(data+len) = 0;

  printf("Reply from %s : %s\n",hname,data);
     
  close(sd); 
  return (0);   
}

