#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>
#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/fcntl.h>
#include <sys/ioctl.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/timeb.h>
#include <ctype.h>
#include <sys/file.h>
#include <sys/stat.h>
#include <strings.h>
#include <sys/socket.h>
#include <arpa/inet.h>


#define HELLO_PORT 12345
#define HELLO_GROUP "225.0.0.37"
#define MSGBUFSIZE 4096

main(int argc, char *argv[])
{
     struct sockaddr_in addr;
     int fd, nbytes,addrlen;
     struct ip_mreq mreq;
     char msgbuf[MSGBUFSIZE];

     if(argc < 2)
     {
         printf("usage: %s <multicast group>\n", argv[0]);
         exit(0);
     }

     /* create what looks like an ordinary UDP socket */
     if ((fd=socket(AF_INET,SOCK_DGRAM,0)) < 0) {
          perror("socket");
          exit(1);
     }

     /* set up destination address */
     memset(&addr,0,sizeof(addr));
     addr.sin_family=AF_INET;
     addr.sin_addr.s_addr=htonl(INADDR_ANY); /* N.B.: differs from sender */
     addr.sin_port=htons(HELLO_PORT);
     
     /* bind to receive address */
     if (bind(fd,(struct sockaddr *) &addr,sizeof(addr)) < 0) {
          perror("bind");
          exit(1);
     }
     
     /* use setsockopt() to request that the kernel join a multicast group */
     mreq.imr_multiaddr.s_addr=inet_addr(argv[1]);
     mreq.imr_interface.s_addr=htonl(INADDR_ANY);
     if (setsockopt(fd, IPPROTO_IP, IP_ADD_MEMBERSHIP, (char *) &mreq,
                                         sizeof(mreq)) < 0) 
     {
          perror("setsockopt");
          exit(1);
     }

     /* now just enter a read-print loop */
     for(;;) {
          addrlen=sizeof(addr);
          if ((nbytes=recvfrom(fd,msgbuf,MSGBUFSIZE,0,
                               (struct sockaddr *) &addr,&addrlen)) < 0) {
               perror("recvfrom");
               exit(1);
          }
          puts(msgbuf);
     }
}

