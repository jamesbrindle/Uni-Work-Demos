/* Example 3: UNIX TCP sockets client  */
/* Client: sends a query to the server, server searches for particular record */
/* in a data file `list.dat`and sends back a whole record extracted as a */
/* response.  */

#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/time.h>

struct hostent *gethostbyname();
struct sockaddr_in makeDestSA( char *hostname, int port);
int  makeLocalS(int port);

#define REC_PORT 1234
#define SIZE 1000
				/* function create Destination Socket Address */

struct sockaddr_in makeDestSA( char *hostname, int port)
{
  struct sockaddr_in sa;
  struct hostent *host;

  if((host= gethostbyname(hostname))==NULL) {
	printf("Unknown host name\n");
	exit(-1);
  }

  sa.sin_family = AF_INET;
  sa.sin_addr=*(struct in_addr *) (host->h_addr);
  sa.sin_port=htons(port);
  return sa;
}

/* function - make client's socket with port number 'int port' */

int makeLocalS(int port)
{ int s;
  struct sockaddr_in sa;

  if((s = socket(AF_INET, SOCK_STREAM,0))<0) {
	perror("socket failed");
	return -1;
  }

				/* Socket address/name */

  sa.sin_family = AF_INET;
  sa.sin_port = htons(port);
  sa.sin_addr.s_addr = htonl(INADDR_ANY);

  if(bind(s,(struct sockaddr *)&sa, sizeof(struct sockaddr_in))!=0){
	perror("Bind failed \n");
	close(s);
	return -1;
  }
  return s;
}

#define REC 200
				/* record structure */

struct info 	{char name1[15];
		 char name2[15];
		 char phone[10];
		 };

int main()
{ int s, n, sk1, i;
  int port = REC_PORT;		/* Server's port */
  struct sockaddr_in sa;	/* socket adress structure */
  struct hostent *host;
  char  h[20];
  struct info x, list1[REC];

  printf("Enter server name:");
  fgets(h, 20, stdin);
  h[strlen(h) - 1] = 0;
				/* replace newline character put in by fgets */
				/* with null */
  sa=makeDestSA(h, port);
				/* client socket */

  if ((s = socket(AF_INET, SOCK_STREAM, 0)) == -1)
	return;
  else {
	printf("First name: ");	/* query input*/
	fgets(x.name1, 15, stdin);
       	printf("Last name:");
       	fgets(x.name2, 15, stdin);
	printf("Phone:");
	fgets(x.phone, 10, stdin);

	x.name1[strlen(x.name1) - 1] = 0;
	x.name2[strlen(x.name2) - 1] = 0;
	x.phone[strlen(x.phone) - 1] = 0;

				/* connect to server */

	if(connect(s, (struct sockaddr *)&sa,sizeof(struct sockaddr_in)) < 0 ) {
		perror("Connection refused\n");
		return;
	}
	else {

				/*send to socket - receive from socket */
		write(s, &x, sizeof(struct info));
		read(s , &sk1, sizeof(int));
		if(sk1 < 0) {
			printf("\nNot found in directory: %s %s %s\n", x.name1,
			    x.name2, x.phone);
		}
		else {
			for(i=0;i<=sk1;i++)
				read(s, &list1[i], sizeof(struct info));

			printf("Retrieved data for: %s; %s; %s;\n",x.name1, 
			    x.name2, x.phone);
			for(i=0;i<=sk1;i++)
				printf("*-*: %s, %s, %s\n",list1[i].name1,
					list1[i].name2, list1[i].phone);

		}
		close(s);
      }
  }
	return;
}


