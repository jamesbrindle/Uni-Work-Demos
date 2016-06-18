/* Example 2: UDP sockets server   */
/* Task: to send a string-message to server and to receive a confirmation. */
/* Server: the program expects a message from a client, displays a text and */
/* sends back to client a message-confirmation. */

#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <sys/time.h>
#include <stdlib.h>

struct hostent *gethostbyname();

#define REC_PORT 1234		/* server's port number */
#define SEN_PORT 1235   	/* client's port number */
#define SIZE 250
				
int main()
{ int s,n,aLength;
  int port = REC_PORT;		/* Server's port number */
  struct sockaddr_in sa, as, aSocketAddress;
  struct hostent *hp, *hs;
  char message[SIZE]="", aknmes[22]="Received, thank you.";
	
  switch (fork())
  { case -1:
	printf(" Unable to fork daemon\n");
	exit(1);
  case 0:

				/* program at server site  creating local socket */
	if((s = socket(AF_INET, SOCK_DGRAM,0))<0) {
		perror(" Receive socket failed");
		return;
	}
	as.sin_family=AF_INET;
	as.sin_port = htons(port);
	as.sin_addr.s_addr = htonl(INADDR_ANY);

	if(bind(s,(struct sockaddr *)&as, sizeof(struct sockaddr_in))!=0){
		perror("Bind at server site failed \n");
		close(s);
		return;
	}
	aSocketAddress.sin_family=AF_INET;   
	for(;;) {

				/* waiting for a message on socket' */
		aLength= sizeof(aSocketAddress);
		if((n = recvfrom(s, message, SIZE, 0, 
		    (struct sockaddr *)&aSocketAddress, &aLength))<0) {
			 perror(".!!!.... recvfrom error"); 
			return;
		 }
		else {
			hp = gethostbyaddr((char *)&aSocketAddress.sin_addr,
			    sizeof (aSocketAddress.sin_addr), AF_INET);
			*(message+n) = 0;                    
			printf("\a\nYou've got message from host '%s'.\n"
				"Received message: %s\n", hp->h_name, message);

				/* how to send a confirmation to client */
			system ("sleep 5");

			sa.sin_family = AF_INET;       
			sa.sin_addr=*(struct in_addr *)(hp->h_addr);  
			sa.sin_port=htons(SEN_PORT); 

			printf("Sent off to client:\n");
			printf("address %s,port %d\n",inet_ntoa(sa.sin_addr),
				ntohs(sa.sin_port));

				/* sending */
			if((n= sendto(s,aknmes,strlen(aknmes),0,
			    (struct sockaddr *)&sa, 
			    sizeof(struct sockaddr_in))) <0 )
				perror("Send failed on Receiver aknowledgment\n");

			printf("Msg back `%s` sent off, length msg `%d` \n",
			    aknmes, n);

		}		/* else end */
	}			/* for end */
	default:
		exit(0);
	}			/*switch end */
}				/*main end*/


