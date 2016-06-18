
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
#define DATA "hello world"

main(int argc, char *argv[])
{
     struct sockaddr_in addr;
     int fd, cnt;
     int i;
     struct ip_mreq mreq;
  
     int ttl=100, opt, optlen;

     if(argc < 3)
     {
         printf("usage: %s <multicast group> <message>\n", argv[0]);
         exit(0);
     }

     /* create what looks like an ordinary UDP socket */
     if ((fd=socket(AF_INET,SOCK_DGRAM,0)) < 0) {
          perror("socket");
          exit(1);
     }
     fprintf(stderr, "%s: socket %d opened successfully\n", argv[0], fd);

     /*if (getsockopt(fd, IPPROTO_IP, IP_TTL, (char *)  &opt,
                             &optlen) < 0)
     {
          perror("getsockopt error");
          exit(0);
     }
     printf("ttl=%d\n", opt);*/


     if (setsockopt(fd, IPPROTO_IP, IP_MULTICAST_TTL, (char *) &ttl,
                             sizeof(ttl)) < 0)
     {
          perror("setsockopt error");
          exit(0);
     }



     /* set up destination address */
     memset(&addr,0,sizeof(addr));
     addr.sin_family=AF_INET;
     addr.sin_addr.s_addr=inet_addr(argv[1]);
     addr.sin_port=htons(HELLO_PORT);
     
     /* now just sendto() our destination! */
     if (sendto(fd, argv[2], 64, 0,(struct sockaddr *) &addr,
                 sizeof(addr)) < 0) {
          perror("sendto");
          exit(1);
     }
     fprintf(stderr, "%s: message sent on group %s\n", argv[0], argv[1]);

     close(fd);
     exit(0);

}
