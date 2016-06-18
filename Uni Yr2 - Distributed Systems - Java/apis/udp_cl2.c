/*   UDP Sockets   Client site     */

#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <sys/time.h>


#define REC_PORT 1234		/* receive-server's port number */
#define SEN_PORT 1235		/* send-client's port number */
#define SIZE 250
				
int main()
{ int s, n, len, any;
  int port = REC_PORT;
  struct sockaddr_in scl, ssr;	/* sock struct for client and server */
				/* from SYS */
  struct hostent *host;		/* struct for host name, from SYS     */
  char message[SIZE], h[20], ack[SIZE];
				/* strings to simulate message exchange      */
  char testchar;

/* socket creation at client site */
/* opens a socket pointer */

  if((s = socket(AF_INET, SOCK_DGRAM, 0))<0) {
				/*parameters needed */
	perror("Client socket creation failed");
	return;
  }

				/*   filling up client's sock structure by  */
  scl.sin_family = AF_INET;
  scl.sin_port = htons(SEN_PORT);
				/* function htons() - host to network short */
  scl.sin_addr.s_addr = htonl(INADDR_ANY);

				/* Binding sock structure and socket */
				/* descriptor */

  if(bind(s,(struct sockaddr *)&scl, sizeof(scl))!=0){
	perror("Bind failed \n");
	close(s);
	return;
  }

				/* get server name and 'open' a socket there */
  printf("Enter host name:");
  fgets(h, 20, stdin);

  h[strlen(h) - 1] = 0;
				/* replace newline character put in by fgets */
				/* with null */

  if((host=gethostbyname(h))==NULL) {
	printf ("Unknown host name\n");
	return;
  }

  ssr.sin_family = AF_INET;
  ssr.sin_port = htons(port);	/*  function htons(): host to network short */
  ssr.sin_addr=*(struct in_addr*) (host->h_addr);

				/* keyin your message  */
  printf("Your message ->");
  fgets(message, SIZE, stdin);

  if((n = sendto(s,message,strlen(message),0,(struct sockaddr *)&ssr,
      sizeof(ssr))) <0 )
	  perror("Send failed\n");

  else
	printf("Msg sent off to host '%s' ....\n", host->h_name); 
	n=0;
	printf ("\n ssr =%d, %s, %d\n", ssr.sin_family, 
	    inet_ntoa(ssr.sin_addr),ntohs(ssr.sin_port));
	if((any = anyThingThere (s)) > 0) {
		len= sizeof (struct sockaddr_in); 
		n = recvfrom(s, ack, SIZE, 0, (struct sockaddr *)&ssr, &len );
		*(ack+n) =0;
		printf("Acknowledgment -> '%s' - %d\n", ack,n);
        }
	else {
		printf("Can't get response from server `%s` ", h);
		printf(" recvfrom returns `%d` \n", n);
	}

	close(s);

	return;
 }

int anyThingThere(int s)
{
  unsigned long read_mask;
  struct timeval timeout;
  int n;
 
  timeout.tv_sec=8;               /* timeout (sec) to expect answer */
  timeout.tv_usec=0;
  read_mask=(1<<s);
 
  if((n = select(32,(fd_set *)&read_mask,0,0,&timeout))<0)
	perror("Select fail:\n");

  return n;
}


