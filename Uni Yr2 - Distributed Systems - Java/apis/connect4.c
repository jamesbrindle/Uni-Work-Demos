/* -------------------------------------------------------------------------- */
/* connectTCP - connect to a specified TCP service on a specified host */
/* -------------------------------------------------------------------------- */
int connectTCP (host, service)
char   *host;			/* name of host to which connection is desired */
/*-------1---------2---------3---------4---------5---------6---------7---------8---------9---------0 */
char   *service;		/* service associated with the desired port   */

{
 return connectsock (host, service, "tcp");
}

/* -------------------------------------------------------------------------- */
/* passiveTCP - perform passive open */
/* -------------------------------------------------------------------------- */
int passiveTCP (service, qlen)
char   *service;		/* service associated with the desired port */
int   qlen;			/* maximum server request queue length      */
{
 return passivesock (service, "tcp", qlen);
}

#include <sys/types.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>

#include <errno.h>

#ifndef   INADDR_NONE
#define   INADDR_NONE   0xffffffff
#endif    /* INADDR_NONE */

/* extern int   errno;
extern const char *const sys_errlist[];
*/

u_long   inet_addr ();
u_short  portbase= 0;		/* port base, for non-root servers   */

/*----------------------------------------------------------------  */
/* passivesock - allocate & bind a server socket using TCP          */
/*----------------------------------------------------------------  */
int passivesock (service, protocol, qlen)
char *service;			/* service associated with the desired port   */
char *protocol;			/* name of protocol to use ("tcp" or "udp")   */
int   qlen;			/* maximum length of the server request queue */
{
  struct servent    *pse;	/* pointer to service information entry */
  struct protoent   *ppe;	/* pointer to protocol information entry*/
  struct sockaddr_in sin;	/* an Internet endpoint address         */
  int    s, type;		/* socket descriptor and socket type    */

  sin.sin_family= AF_INET;
  sin.sin_addr.s_addr= INADDR_ANY;

				/* Map service name to port number  */
  if (pse= getservbyname (service, protocol))
	sin.sin_port= htons (ntohs ((u_short) pse->s_port) + portbase);
	else if ((sin.sin_port = htons ((u_short) atoi (service))) == 0)
		errexit ("can't get \"%s\" service entry\n", service);

				/* Map protocol name to protocol number  */
  if ((ppe = getprotobyname (protocol)) == 0)
	errexit ("can't get \"%s\" protocol entry\n", protocol);

				/* Use protocol to choose a socket type  */
  if (strcmp (protocol, "udp") == 0)
	type = SOCK_DGRAM;
  else
	type = SOCK_STREAM;

				/* Allocate a socket   */
  s= socket (PF_INET, type, ppe->p_proto);
  if (s < 0)
	errexit ("can't create socket: %s\n", strerror(errno));

				/* Bind the socket  */
  if (bind (s, (struct sockaddr *)&sin, sizeof (sin)) < 0)
	errexit ("can't bind to %s port: %s %s num: %d\n", service,
			strerror(errno), " ", ntohs(sin.sin_port));
  if (type == SOCK_STREAM && listen (s, qlen) < 0)
	errexit ("can't listen on %s port: %s\n", service,
			strerror(errno));
  return s;
}

/*------------------------------------------------------------  */
/* connectsock - allocate and connect a socket using TCP          */
/*------------------------------------------------------------  */

int connectsock (host, service, protocol)
char   *host;      /* name of host to which connection is desired*/
char   *service;   /* service associated with the desired port   */
char   *protocol;  /* name of protocol to use ("tcp" or "udp")   */

{
  struct hostent    *phe;	/* pointer to host information entry    */
  struct servent    *pse;	/* pointer to service information entry */
  struct protoent   *ppe;	/* pointer to protocol information entry*/
  struct sockaddr_in sin;	/* an Internet endpoint address         */

  int    s, type;		/* socket descriptor and socket type   */

  memset (&sin, 0, sizeof(sin));
  sin.sin_family = AF_INET;

				/* Map service name to port number   */
  if (pse= getservbyname (service, protocol))
	sin.sin_port= pse->s_port;
  else if ((sin.sin_port= htons ((u_short) atoi (service)))== 0)
	errexit ("can't get \"%s\" service entry\n", service);

				/* Map host name to IP address, allowing for dotted decimal  */
  if (phe= gethostbyname (host)) {
	memcpy (&sin.sin_addr, phe->h_addr, phe->h_length);
  } 
  else {
		if ((sin.sin_addr.s_addr = inet_addr(host)) == INADDR_NONE )
			errexit ("can't get \"%s\" host entry\n", host);
  }
     
				/* Map protocol name to protocol numbear   */
  if ((ppe = getprotobyname (protocol)) == 0)
	errexit ("can't get \"%s\" protocol entry\n", protocol);

				/* Use protocol to choose a socket type  */
  if (strcmp (protocol, "udp") == 0)
	type= SOCK_DGRAM;
  else
	type= SOCK_STREAM;

				/* Allocate a socket  */
  s= socket (AF_INET, type, ppe->p_proto);
  if (s < 0)
	errexit ("can't create socket: %s\n", strerror(errno));

				/* Connect the socket  */
  if (connect (s, (struct sockaddr *)&sin, sizeof (sin)) < 0)
	errexit ("can't connect to %s.%s: %s\n", host, service, strerror(errno));

  return s;
}

