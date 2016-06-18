/* - - - - - - - - - - - - - - - - - - - - - - - - - - -*/ 
/* Example 3: Iterative TCP socket server               */
/* - - - - - - - - - - - - - - - - - - - - - - - - - - -*/
/* Task: there is a directory file 'list.dat'    */
/* Each directory record contains person's name, surname, phone number. */
/* Server responds to a query from client by sending back full record(s)  */
 
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <sys/time.h>
#include <stdlib.h>

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

				/* function - make client's socket with port */
				/* number 'int port' */

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

struct info    {char name1[15];	
		char name2[15];
		char phone[10];
		};

int get_all_list(struct info list[REC], int *sk);
int get_list( struct info *x, struct info list[REC], int sk,
    struct info list1[REC], int *sk1);

int main()
 { int s, i, s_c,n,aLength;
  int port = REC_PORT;		/* number of server port*/
  struct sockaddr_in SA, aSocketAddress;
				/* characteristic socket's structures */
  struct hostent *hp;		/* host structure */
  char message[SIZE]="", aknmes[13]="Received OK!";

  struct info list[REC], list1[REC];
  struct info x;
  int sk, sk1;

  if(!get_all_list(list,&sk)) {
	printf("Data file error !!!");
	return ;
  };

  switch (fork()) {
  case -1:
	printf(" unable to fork daemon\n");
	exit;
  case 0:

				/* resident part of the server program */

	aLength = sizeof(aSocketAddress);
	aSocketAddress.sin_family=AF_INET;
	if ((s = makeLocalS( port)) == -1){
				/* socket creation */
		perror("can't create receiver socket");
		return; }
	else
		listen(s ,10);

	for(;;) {
		if((s_c = accept(s, (struct sockaddr *)&aSocketAddress,
		    &aLength))<0) {
			perror("Can't accept connection");
			return ;
		}
		 else {
			read(s_c ,&x, sizeof(struct info) );
				/* extracts the name of the client which sent */
				/* a query using client's address - */
				/* aSocketAddress */

			hp = gethostbyaddr((char *)&aSocketAddress.sin_addr,
			    sizeof (aSocketAddress.sin_addr), AF_INET);
			printf("\a\nYou've got request from host `%s`. \n"
				"Received data: %s; %s; %s;\n",
				hp->h_name, x.name1, x.name2, x.phone);
						
			get_list( &x,list,sk,list1,&sk1);

			write(s_c, &sk1, sizeof( int));
			for(i=0; i<=sk1;i++)
				write(s_c, &list1[i], sizeof(struct info));
			close(s_c);
		}
	}
  default:
	exit;
  }
 }
 /*-------------------- end of main ---------------------*/

int get_all_list(struct info list[REC], int *sk)
				/* loading data from LIST.DAT */
{
  FILE *in;
  if ((in = fopen("list.dat", "rt")) == NULL) {
	return (0);
  };
  *sk=-1;
  while (!feof(in)) {
	(*sk)++;
	strcpy(list[*sk].name1,"");
	strcpy(list[*sk].name2,"");
	strcpy(list[*sk].phone,"");
	fscanf(in, "%s%s%s", list[*sk].name1,
	    list[*sk].name2,list[*sk].phone);
	    list[*sk].phone[strlen(list[*sk].phone)]=0;
	    list[*sk].name1[strlen(list[*sk].name1)]=0;
	    list[*sk].name2[strlen(list[*sk].name2)]=0;
  };

  fclose(in);
  return 1;
}

				/* Search function */
int get_list( struct info *x, struct info list[200], int sk, 
    struct info list1[200], int *sk1) {
  int i,zyme;
  *sk1 = -1;
  for( i=0 ; i<=sk ; i++) {
	zyme=0;
	if ( !(x->name1[0] == 0))
		if( strcmp(x->name1,list[i].name1) ) zyme++;
	if ( !(x->name2[0] == 0 ))
		if( strcmp(x->name2,list[i].name2) ) zyme++;
	if ( !(x->phone[0] == 0))
		if( strcmp(x->phone,list[i].phone) ) zyme++;
	if( zyme == 0) {
		(*sk1)++;
		strcpy(list1[*sk1].name1,list[i].name1);
		strcpy(list1[*sk1].name2,list[i].name2);
		strcpy(list1[*sk1].phone,list[i].phone);
	};
  };
  return *sk1;
}


